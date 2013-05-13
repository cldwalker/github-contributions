(ns github-contributions.service-test
  (:require [clojure.test :refer :all]
            [io.pedestal.service.test :refer :all]
            [io.pedestal.service.http :as bootstrap]
            [github-contributions.service :as service]))

(def service
  (::bootstrap/service-fn (bootstrap/create-servlet service/service)))

(deftest home-page-test
  (is (.contains
       (:body (response-for service :get "/"))
       "Contributions on Github"))
  (is (=
       (:headers (response-for service :get "/"))
       {"Content-Type" "text/html"})))