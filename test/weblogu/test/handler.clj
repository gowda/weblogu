(ns weblogu.test.handler
  (:use [weblogu.handler]
        [clojure.test]))

(deftest test-index
  (is (map? (handler {:uri "/" :request-method :get})))
  (is (= (:status (handler {:uri "/" :request-method :get})) 200))
  (is (not (nil? (:body (handler {:uri "/" :request-method :get})))))
  (is (map? (handler {:uri "/" :request-method :post})))
  (is (= (:status (handler {:uri "/" :request-method :post})) 405)))

(deftest test-add
  (is (map? (handler {:uri "/add" :request-method :get})))
  (is (= (:status (handler {:uri "/add" :request-method :get})) 200))
  (is (not (nil? (:body (handler {:uri "/add" :request-method :get})))))
  (is (map? (handler {:uri "/add" :request-method :post})))
  (is (= (:status (handler {:uri "/add" :request-method :post})) 302))
  (is (map? (:headers (handler {:uri "/add" :request-method :post})))))
