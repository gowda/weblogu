(ns weblogu.test.handler
  (:use [weblogu.handler]
        [clojure.test]
        [ring.mock.request :only [request]]))

(deftest test-index
  (testing "/"
    (testing "GET method."
      (let [r (handler (request :get "/"))]
        (is (map? r) "should return a map.")
      (is (= (:status r) 200) "should have status code 200.")
      (is (not (nil? (:body r))) "body should not be null.")))
    (testing "POST method."
      (let [r (handler (request :post "/"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 405) "should have status code 405.")))))

(deftest test-add
  (testing "/add"
    (testing "GET method."
      (let [r (handler (request :get "/add"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 200) "should have status code 200.")
        (is (not (nil? (:body r))) "body should not be null.")))
    (testing "POST method."
      (let [r (handler (request :post "/add"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 302) "should have status code 302.")
        (is (map? (:headers r)) "should return a map of headers.")))))

(deftest test-list
  (testing "/list"
    (testing "GET method."
      (let [r (handler (request :get "/list"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 200) "should have status code 200.")
        (is (not (nil? (:body r))) "body should not be null.")))
    (testing "POST method."
      (let [r (handler (request :post "/list"))]
        (is (map? r) "should return a map.")
        (is (= (:status r) 405) "should have status code 405.")
        (is (map? (:headers r)) "should return a map of headers.")))))
