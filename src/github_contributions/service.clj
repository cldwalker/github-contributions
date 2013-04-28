(ns github-contributions.service
    (:require [io.pedestal.service.http :as bootstrap]
              [io.pedestal.service.http.route :as route]
              [io.pedestal.service.http.body-params :as body-params]
              [io.pedestal.service.http.route.definition :refer [defroutes]]
              [io.pedestal.service.http.sse :as sse]
              [io.pedestal.service.log :as log]
              [com.github.ragnard.hamelito.hiccup :as haml]
              [tentacles.repos :refer [user-repos contributors]]
              [clojure.java.io :as io]
              [ring.util.response :as ring-resp]))

(defn home-page
  [request]
  (ring-resp/response
   (haml/html (slurp (io/resource "public/index.haml")))))

(defn send-counter
  "Counts down to 0, sending value of counter to sse context and
  recursing on a different thread; ends event stream when counter
  is 0."
  [ctx count]
  (sse/send-event ctx "message" (str count ", thread: " (.getId (Thread/currentThread))))
  (Thread/sleep 2000)
  (if (> count 0)
    (future (send-counter ctx (dec count)))
    (sse/end-event-stream ctx)))

(defn gh-auth
  []
  {:auth (or (System/getenv "GITHUB_AUTH")
             (throw (ex-info "Set $GITHUB_AUTH to basic auth in order to use github api." {})))})

(defn fetch-repos [user]
  (user-repos user (assoc (gh-auth) :all-pages true)))

(defn fetch-contributions [sse-context user]
  (if user
    (let [repos (fetch-repos user)
          forked (filter :fork repos)]
      (sse/send-event sse-context "message" (format "Found %s repositories, %s forked repositories: %s"
                                                    (count repos) (count forked) (pr-str (mapv :name forked)))))
    (log/error :msg "No user given to fetch contributions. Ignored.")))

(def ^{:doc "Map of IDs to SSE contexts"} subscribers (atom {}))

(defn contributions-page
  "Starts sending counter events to client."
  [sse-context]
  (if-let [id (get-in sse-context [:request :query-params :id])]
    (swap! subscribers assoc id sse-context)
    (log/error :msg "No id passed to /contributions. Ignored.")))

(defn update-contributions [request]
  (if-let [id (get-in request [:form-params "id"])]
    (if-let [sse-context (get @subscribers id)]
      (fetch-contributions sse-context (get-in request [:form-params "user"]))
      #_(send-counter sse-context 10)
      (log/error :msg (str "No sse context for id " id)))
    (log/error :msg "No id passed to update contributions. Ignored.")))

(defroutes routes
  [[["/" {:get home-page}
     ;; Set default interceptors for /about and any other paths under /
     ^:interceptors [(body-params/body-params) bootstrap/html-body]
     ["/contributions" {:get [::contributions (sse/start-event-stream contributions-page)]
                        :post update-contributions}]]]])

;; You can use this fn or a per-request fn via io.pedestal.service.http.route/url-for
(def url-for (route/url-for-routes routes))

;; Consumed by github-contributions.server/create-server
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; :bootstrap/interceptors []
              ::bootstrap/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::boostrap/allowed-origins ["scheme://host:port"]

              ;; Root for resource interceptor that is available by default.
              ::bootstrap/resource-path "/public"

              ;; Either :jetty or :tomcat (see comments in project.clj
              ;; to enable Tomcat)
              ;;::bootstrap/host "localhost"
              ::bootstrap/type :jetty
              ::bootstrap/port (Integer. (or (System/getenv "PORT") 8080))})
