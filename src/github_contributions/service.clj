(ns github-contributions.service
    (:require [io.pedestal.service.http :as bootstrap]
              [io.pedestal.service.http.route :as route]
              [io.pedestal.service.http.body-params :as body-params]
              [io.pedestal.service.http.route.definition :refer [defroutes]]
              [io.pedestal.service.http.sse :as sse]
              [io.pedestal.service.log :as log]
              [com.github.ragnard.hamelito.hiccup :as haml]
              [clojure.java.io :as io]
              [github-contributions.github :refer [stream-contributions get-in!]]
              [ring.util.response :as ring-resp]))

(defn home-page
  [request]
  (ring-resp/response
   (haml/html (slurp (io/resource "public/index.haml")))))

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
      (stream-contributions sse/send-event sse-context (get-in! request [:form-params "user"]))
      (log/error :msg (str "No sse context for id " id)))
    (log/error :msg "No id passed to update contributions. Ignored.")))

(defroutes routes
  [[["/"
     ;; Set default interceptors for /about and any other paths under /
     ^:interceptors [(body-params/body-params) bootstrap/html-body]
     ["/contributions" {:get [::contributions (sse/start-event-stream contributions-page)]
                        :post update-contributions}]
     ["/*user" {:get home-page}]]]])

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
