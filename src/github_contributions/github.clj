(ns github-contributions.github
  (:require [tentacles.repos :refer [user-repos contributors specific-repo]]
            [io.pedestal.service.log :as log]
            [com.github.ragnard.hamelito.hiccup :as haml]
            [clostache.parser :as clostache]))

;;; helpers
(defn gh-auth
  []
  {:auth (or (System/getenv "GITHUB_AUTH")
             (throw (ex-info "Set $GITHUB_AUTH to basic auth in order to use github api." {})))})

(defn get-in!
  "Fail fast if no truish value for get-in"
  [m ks]
  (or (get-in m ks) (throw (ex-info "No value found for nested keys in map" {:map m :keys ks}))))

(defn get!
  "Fail fast if no truish value for get"
  [m k]
  (or (get m k) (throw (ex-info "No value found for key in map" {:map m :key k}))))

;;; api calls
(defn fetch-repos [user]
  (user-repos user (assoc (gh-auth) :all-pages true)))

;;; get around tentacles bug
(defn fetch-contributors [user repo]
  (let [cs (contributors user repo (gh-auth))]
    (if (= (last cs) {})
      (vec (drop-last 1 cs))
      cs)))

(defn fetch-fork-info [user repo]
  (log/info :msg (format "Fetching fork info for %s/%s" user repo))
  (let [repo-map (specific-repo user repo (gh-auth))
        full-name (get-in! repo-map [:parent :full_name])
        [_ parent-user parent-repo] (re-find #"([^/]+)/([^/]+)" full-name)
        contribs (->> (fetch-contributors parent-user parent-repo)
                      (sort-by :contributions)
                      reverse
                      (map-indexed (fn [num elem] (assoc elem :num num))))
        contributor (some #(and (= user (:login %)) %) contribs)]
    {:full-name full-name
     :user user
     :commits (or (:contributions contributor) 0)
     :tr-class (if (:contributions contributor) "contribution" "no-contribution")
     :total-contributors (count contribs)
     :contributor-rank (when contributor (inc (:num contributor)))
     :stars (get-in repo-map [:parent :watchers_count])
     :desc (get-in repo-map [:parent :description])}))

(def memoized-fetch-fork-info (memoize fetch-fork-info))

(defn- rank-ending [num]
  (cond
   (and (= \3 (last num)) (not= '(\1 \3) (take-last 2 num))) "rd"
   (and (= \2 (last num)) (not= '(\1 \2) (take-last 2 num))) "nd"
   (and (= \1 (last num)) (not= '(\1 \1) (take-last 2 num))) "st"
   :else "th"))

(defn- render-row [fork-map]
  (haml/html
   (clostache/render-resource
    "public/row.haml"
    (assoc fork-map
      :ranking (if (:contributor-rank fork-map)
                 (format "%s%s of %s"
                         (:contributor-rank fork-map)
                         (rank-ending (str (:contributor-rank fork-map)))
                         (:total-contributors fork-map))
                 "-")
      :ranking-class (if-not (:contributor-rank fork-map) "hide" "")))))

(def ^{:doc "Beginning message of a contributions stream"} begin-msg ":BEGIN:")
(def ^{:doc "Ending message of a contributions stream"} end-msg ":END:")

(defn- render-end-msg [user forks]
  (format
   "%s <a href=\"https://github.com/%s\">%s</a> has contributed to %s of %s forks."
   end-msg
   user
   user
   (count (filter #(pos? (:commits %)) forks))
   (count forks)))

(defn- fetch-fork-info-and-send-msg [message-event user fork]
  (let [fork-map (memoized-fetch-fork-info user (get! fork :name))]
    (message-event (render-row fork-map))
    fork-map))

(defn stream-contributions [send-event-fn sse-context user]
  (if user
    ;; TODO: remove limit
    (let [repos (take 20 (fetch-repos user))
          forked-repos (filter :fork repos)
          message-event (partial send-event-fn sse-context "message")]
      (message-event
       (format "%s %s has %s forks. Fetching data..."
               begin-msg user (count forked-repos)))
      (->> forked-repos
           (mapv (partial fetch-fork-info-and-send-msg message-event user))
           (render-end-msg user)
           message-event))
    (log/error :msg "No user given to fetch contributions. Ignored.")))