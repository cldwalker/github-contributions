(ns github-contributions.github
  (:require [tentacles.repos :refer [user-repos contributors specific-repo]]
            [io.pedestal.service.log :as log]
            [com.github.ragnard.hamelito.hiccup :as haml]
            [clostache.parser :as clostache]))

;;; helpers
(defn gh-auth
  "Sets github authentication using $GITHUB_AUTH. Its value can be a basic auth user:pass
or an oauth token."
  []
  (if-let [auth (System/getenv "GITHUB_AUTH")]
    (if (.contains auth ":") {:auth auth} {:oauth-token auth})
    (or (throw (ex-info "Set $GITHUB_AUTH to an oauth token or basic auth in order to use Github's api." {})))))

(defn get-in!
  "Fail fast if no truish value for get-in"
  [m ks]
  (or (get-in m ks) (throw (ex-info "No value found for nested keys in map" {:map m :keys ks}))))

(defn get!
  "Fail fast if no truish value for get"
  [m k]
  (or (get m k) (throw (ex-info "No value found for key in map" {:map m :key k}))))

;;; API calls
(defn- github-api-call
  "Wraps github api calls to handle unsuccessful responses."
  [f & args]
  (let [response (apply f args)]
    (if (some #{403 404} [(:status response)])
      (throw (ex-info
              (if (= 403 (:status response))
                "Rate limit has been exceeded for Github's API. Please try again later."
                (format "Received a %s from Github. Please try again later." (:status response)))
              {:reason :github-client-error :response response}))
      response)))

(defn fetch-repos
  "Fetch all public repositories for a user"
  [user]
  (github-api-call user-repos user (assoc (gh-auth) :all-pages true)))

(defn fetch-contributors
  "Wrap around a tentacles bug for the contributors endpoint where an extra {} appears at the end."
  [user repo]
  (let [cs (github-api-call contributors user repo (gh-auth))]
    (if (= (last cs) {})
      (vec (drop-last 1 cs))
      cs)))

(defn fetch-fork-info
  "Gathers a fork's contribution stats from fork and contributors api calls."
  [user repo]
  (log/info :msg (format "Fetching fork info for %s/%s" user repo))
  (let [repo-map (github-api-call specific-repo user repo (gh-auth))
        full-name (get-in! repo-map [:parent :full_name])
        [_ parent-user parent-repo] (re-find #"([^/]+)/([^/]+)" full-name)
        contribs (->> (fetch-contributors parent-user parent-repo)
                      (sort-by :contributions)
                      reverse
                      (map-indexed (fn [num elem] (assoc elem :num num))))
        contributor (some #(and (= user (:login %)) %) contribs)]
    {:full-name full-name
     :fork-name (:full_name repo-map)
     :user user
     :commits (or (:contributions contributor) 0)
     :commit-word (if (= 1 (:contributions contributor)) "commit" "commits")
     :tr-class (if (:contributions contributor) "contribution" "no-contribution")
     :total-contributors (count contribs)
     :contributor-rank (when contributor (inc (:num contributor)))
     :stars (get-in repo-map [:parent :watchers_count])
     :desc (get-in repo-map [:parent :description])}))

;;; Cache api calls for maximum reuse. Of course, this cache only lasts
;;; as long as the app lives.
(def memoized-fetch-fork-info (memoize fetch-fork-info))
(def memoized-fetch-repos (memoize fetch-repos))

(defn rank-ending
  "Adds an approprate string suffix to a number e.g. nd for 2nd."
  [num]
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

(defn- render-end-msg
  "Build final message summarizing contributions to a user's forks."
  [user forks]
  (format
   "<a href=\"https://github.com/%s\">%s</a> has contributed to %s of %s forks."
   user
   user
   (count (filter #(pos? (:commits %)) forks))
   (count forks)))

(defn- fetch-fork-and-send-row [send-to user fork]
  (let [fork-map (memoized-fetch-fork-info user (get! fork :name))]
    (send-to "results" (render-row fork-map))
    fork-map))

(defn- stream-contributions*
  "Sends 3 different sse events (message, results, end-message) depending on
what part of the page it's updating."
  [send-event-fn sse-context user]
  ;; TODO: remove limit
  (let [repos (take 20 (memoized-fetch-repos user))
        forked-repos (filter :fork repos)
        send-to (partial send-event-fn sse-context)]
    (send-to "message"
             (format "%s has %s forks. Fetching data..."
                     user (count forked-repos)))
    (->> forked-repos
         (mapv (partial fetch-fork-and-send-row send-to user))
         (render-end-msg user)
         (send-to "message"))
    (send-to "end-message" user)))

(defn stream-contributions
  "Streams a user's contributions with a given fn and sse-context."
  [send-event-fn sse-context user]
  (if user
    (try
      (stream-contributions* send-event-fn sse-context user)
      {:status 200}
      (catch clojure.lang.ExceptionInfo exception
        (log/error :msg (str "40X response from Github: " (pr-str (ex-data exception))))
        (send-event-fn sse-context "error"
                       (if (= :github-client-error (:reason (ex-data exception)))
                         (.getMessage exception)
                         "An unexpected error occurred while contacting Github. Please try again later."))))
    (log/error :msg "No user given to fetch contributions. Ignored.")))