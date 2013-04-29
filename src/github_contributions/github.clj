(ns github-contributions.github
  (:require [tentacles.repos :refer [user-repos contributors specific-repo]]
            [io.pedestal.service.log :as log]))


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

(defn fetch-repos [user]
  (user-repos user (assoc (gh-auth) :all-pages true)))

(defn fetch-fork-info [user repo]
  (log/info :msg (format "Fetching fork info for %s/%s" user repo))
  (let [repo-map (specific-repo user repo (gh-auth))
        full-name (get-in! repo-map [:parent :full_name])
        [_ parent-user parent-repo] (re-find #"([^/]+)/([^/]+)" full-name)
        contribs (contributors parent-user parent-repo (gh-auth))
        commits (some #(and (= user (:login %)) (:contributions %)) contribs)]
    {:full_name full-name :commits (or commits "-")}))

(defn fetch-contributions [send-event-fn sse-context user]
  (if user
    ;; TODO: remove limit
    (let [repos (take 20 (fetch-repos user))
          forked (filter :fork repos)
          message-event (partial send-event-fn sse-context "message")]
      (message-event
       (format "Found %s repositories, %s forked repositories: %s"
               (count repos) (count forked) (pr-str (mapv :name forked))))
      (doseq [fork forked]
        (let [fork-map (fetch-fork-info user (get! fork :name))]
          (message-event
           (format "Fork: %s, Commits: %s"
                   (:full_name fork-map)
                   (:commits fork-map))))))
    (log/error :msg "No user given to fetch contributions. Ignored.")))