(ns weblogu.test.handler
  (:use [weblogu.handler]
        [clojure.test]
        [ring.mock.request :only [request]]))

(deftest test-index
  (is (map? (handler (request :get "/"))))
  (is (= (:status (handler (request :get "/")) 200)))
  (is (not (nil? (:body (handler (request :get "/"))))))
  (is (map? (handler (request :post "/"))))
  (is (= (:status (handler (request :post "/"))) 405)))

(deftest test-add
  (is (map? (handler (request :get "/add"))))
  (is (= (:status (handler (request :get "/add"))) 200))
  (is (not (nil? (:body (handler (request :get "/add"))))))
  (is (map? (handler (request :post "/add"))))
  (is (= (:status (handler (request :post "/add"))) 302))
  (is (map? (:headers (handler (request :post "/add"))))))
