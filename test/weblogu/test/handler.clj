(ns weblogu.test.handler
  (:use [weblogu.handler]
        [clojure.test]
        [ring.mock.request :only [request]]))

(defn test-ring-success [response]
  (is (map? response) "should return a map.")
  (is (= (:status response) 200) "should have status code 200.")
  (is (not (nil? (:body response))) "body should not be nil."))

(deftest test-index
  (testing "/"
    (testing "GET method."
      (let [r (handler (request :get "/"))]
        (test-ring-success r)))
    (testing "POST method."
      (let [r (handler (request :post "/"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 405) "should have status code 405.")))))

(deftest test-add
  (testing "/add"
    (testing "GET method."
      (let [r (handler (request :get "/add"))]
        (test-ring-success r)))
    (testing "POST method."
      (let [r (handler (request :post "/add"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 302) "should have status code 302.")
        (is (map? (:headers r)) "should return a map of headers.")))))

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
