(ns github-contributions.github-test
  (:require [clojure.test :refer :all]
            [github-contributions.service :as service]
            [conjure.core :refer :all]
            [tentacles.repos :as repos]
            [github-contributions.fixtures :as fixtures]
            [github-contributions.github :as github]))

(defn send-event-fn [& args])
(def sse-context {:request {}})

(defn stream-contributions
  []
  (github/stream-contributions send-event-fn sse-context "defunkt"))

(deftest stream-contributions-receives-403-from-github
  []
  (with-redefs [repos/user-repos (constantly fixtures/response-403)]
    (mocking [send-event-fn]
             (stream-contributions)
             (verify-nth-call-args-for 1 send-event-fn sse-context "error"
                                       "Rate limit has been exceeded for Github's API. Please try again later."))))

(deftest stream-contributions-receives-404-from-github
  []
  (with-redefs [repos/user-repos (constantly fixtures/response-404)]
    (mocking [send-event-fn]
             (stream-contributions)
             (verify-nth-call-args-for 1 send-event-fn sse-context "error"
                                       "Received a 404 from Github. Please try again later."))))

;; because conjure only supports =
(def expected-row
  "<tr class=\"contribution\"><td><a href=\"https://github.com/ajaxorg/ace\">ajaxorg/ace</a><span class=\"fork\">&nbsp;(<a href=\"https://github.com/defunkt/ace\" title=\"defunkt/ace\">fork</a>)</span><span class=\"stars\">&nbsp;5188 stars</span></td><td><a href=\"https://github.com/ajaxorg/ace/commits?author=defunkt\">5 commits</a></td><td><a class=\"ranking \" href=\"https://github.com/ajaxorg/ace/contributors\">1st of 2</a></td><td>Ajax.org Cloud9 Editor</td></tr>")

(deftest stream-contributions-receives-200s-from-github
  []
  (with-redefs [repos/user-repos (constantly fixtures/response-user-repos)
                repos/specific-repo (constantly fixtures/response-specific-repo)
                repos/contributors (constantly fixtures/response-contributors)]
    (mocking [send-event-fn]
             (stream-contributions)
             (verify-nth-call-args-for 1 send-event-fn sse-context "message"
                                       "defunkt has 1 forks. Fetching data...")
             (verify-nth-call-args-for 2 send-event-fn sse-context "results" expected-row)
             (verify-nth-call-args-for 3 send-event-fn sse-context "message"
                                       "<a href=\"https://github.com/defunkt\">defunkt</a> has contributed to 1 of 1 forks.")
             (verify-nth-call-args-for 4 send-event-fn sse-context "end-message" "defunkt"))))

(deftest rank-ending-test
  (are [num ending]
       (is (= ending (github/rank-ending num)))
       "1" "st"
       "11" "th"
       "121" "st"
       "2" "nd"
       "12" "th"
       "122" "nd"
       "3" "rd"
       "13" "th"
       "123" "rd"
       "4" "th"))