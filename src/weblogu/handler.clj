(ns weblogu.handler
  (:require [weblogu.blog :as blog])
  (:use [weblogu.model :only [load-entries]]
        [ring.util.response :only [response redirect]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [ring.middleware.resource :only [wrap-resource]]
        [net.cgrand.moustache :only [app]]))

(def handler
  (app (wrap-resource "public") wrap-params wrap-keyword-params
       [""] {:get (fn [req] (blog/home-get (:params req)))}
       ["add"] {:get (fn [req] (blog/add-get (:params req)))
                :post (fn [req] (blog/add-post (:params req)))}
       ["list"] {:get (fn [req] (blog/list-get (:params req)))}))

(defn init []
  (load-entries))
