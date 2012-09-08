(ns weblogu.test.handler
  (:require [net.cgrand.enlive-html :as enlive])
  (:use [weblogu.handler]
        [clojure.test]
        [ring.mock.request :only [request body]]))

(defn- string->dom-tree [string]
  (with-open [s (-> (java.io.StringReader. string) java.io.BufferedReader.)]
    (enlive/html-resource s)))

(defn- links-on-navbar [body]
  (let [link-nodes (enlive/select (string->dom-tree body)
                                  [:div.navbar :div.container :ul :li :a])]
    (map #(first (enlive/attr-values % :href)) link-nodes)))

(defn- navbar-healthy? [body]
  (let [links #{"/" "/add" "/list"}
        on-navbar (links-on-navbar body)]
    (every? links on-navbar)))

(defn test-ring-success [response]
  (is (map? response) "should return a map.")
  (is (= (:status response) 200) "should have status code 200.")
  (is (not (nil? (:body response))) "body should not be nil."))

(deftest test-index
  (testing "/"
    (testing "GET method."
      (let [r (handler (request :get "/"))]
        (test-ring-success r)
        (is (navbar-healthy? (:body r)) "navbar should contain expected links")))
    (testing "POST method."
      (let [r (handler (request :post "/"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 405) "should have status code 405.")))))

(deftest test-add
  (testing "/add"
    (testing "GET method."
      (let [r (handler (request :get "/add"))]
        (test-ring-success r)
        (is (navbar-healthy? (:body r)) "navbar should contain expected links")))
    (testing "POST method"
      ;; (testing "without any form data"
      ;;   (let [r (handler (request :post "/add"))]
      ;;     (test-ring-success r)
      ;;     (is (navbar-healthy? (:body r)) "navbar should contain expected links")
      ;;     (is (form-with-error? (:body r)))))
      (testing "with form data"
        (let [v {:title "Blog entry title from tests"
                 :body "Blog entry body from tests"}
              r (handler (body (request :post "/add") v))]
          (is (map? r) "should return a map.")
          (is (= (:status r) 302) "should have status code 302.")
          (is (map? (:headers r)) "should return a map of headers."))))))

(deftest test-list
  (testing "/list"
    (testing "GET method."
      (let [r (handler (request :get "/list"))]
        (test-ring-success r)))
    (testing "POST method."
      (let [r (handler (request :post "/list"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 405) "should have status code 405.")
        (is (map? (:headers r)) "should return a map of headers.")))))
