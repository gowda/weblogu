(ns weblogu.handler
  (:require [weblogu.blog :as blog])
  (:use [weblogu.model :only [load-entries]]
        [ring.util.response :only [response redirect]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [ring.middleware.resource :only [wrap-resource]]
        [net.cgrand.moustache :only [app]]))

(defn wrap-only-params
  "Ring middleware to pass only :params to the procedure called."
  [handler]
  (fn [req]
    (handler (:params req))))

(def handler
  (app (wrap-resource "public") wrap-params wrap-keyword-params
       [""] {:get (wrap-only-params blog/home-get)}
       ["add"] {:get (wrap-only-params blog/add-get)
                :post (wrap-only-params blog/add-post)}
       ["list"] {:get (wrap-only-params blog/list-get)}))

(defn init []
  (load-entries))
