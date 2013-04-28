(ns github-contributions.service
    (:require [io.pedestal.service.http :as bootstrap]
              [io.pedestal.service.http.route :as route]
              [io.pedestal.service.http.body-params :as body-params]
              [io.pedestal.service.http.route.definition :refer [defroutes]]
              [io.pedestal.service.http.sse :as sse]
              [com.github.ragnard.hamelito.hiccup :as haml]
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

(defn contributions-page
  "Starts sending counter events to client."
  [ctx]
  (send-counter ctx 10))

(defroutes routes
  [[["/" {:get home-page}
     ;; Set default interceptors for /about and any other paths under /
     ^:interceptors [(body-params/body-params) bootstrap/html-body]
     ["/contributions" {:get [::contributions (sse/start-event-stream contributions-page)]}]]]])

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
