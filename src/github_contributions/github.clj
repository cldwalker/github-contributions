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

(defn fetch-fork-info [user repo]
  (log/info :msg (format "Fetching fork info for %s/%s" user repo))
  (let [repo-map (specific-repo user repo (gh-auth))
        full-name (get-in! repo-map [:parent :full_name])
        [_ parent-user parent-repo] (re-find #"([^/]+)/([^/]+)" full-name)
        contribs (->> (contributors parent-user parent-repo (gh-auth))
                      (sort-by :contributions)
                      reverse
                      (map-indexed (fn [num elem] (assoc elem :num num))))
        contributor (some #(and (= user (:login %)) %) contribs)]
    {:full-name full-name
     :user user
     :commits (or (:contributions contributor) 0)
     :total-contributors (count contribs)
     :contributor-rank (when contributor (inc (:num contributor)))
     :stars (get-in repo-map [:parent :watchers_count])
     :desc (get-in repo-map [:parent :description])}))

(def memoized-fetch-fork-info (memoize fetch-fork-info))

(defn stream-contributions [send-event-fn sse-context user]
  (if user
    ;; TODO: remove limit
    (let [repos (take 20 (fetch-repos user))
          forked (filter :fork repos)
          message-event (partial send-event-fn sse-context "message")]
      (log/info :msg
       (format "Found %s repositories, %s forked repositories: %s"
               (count repos) (count forked) (pr-str (mapv :name forked))))
      (doseq [fork forked]
        (let [fork-map (memoized-fetch-fork-info user (get! fork :name))]
          (message-event
           (haml/html
            (clostache/render-resource
             "public/row.haml"
             (assoc fork-map
               :ranking (if (:contributor-rank fork-map)
                          (format "%s out of %s" (:contributor-rank fork-map) (:total-contributors fork-map))
                          "-"))))))))
    (log/error :msg "No user given to fetch contributions. Ignored.")))