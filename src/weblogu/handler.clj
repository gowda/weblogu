(ns weblogu.handler
  (:require [net.cgrand.enlive-html :as enlive])
  (:use [ring.util.response :only [response redirect]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [net.cgrand.moustache :only [app]]))

(def ^:private posts (atom []))

(enlive/defsnippet post "templates/index.html" [:ul#posts]
  [post]
  [:li.title] (enlive/content (:title post)))

(enlive/deftemplate index "templates/index.html" [posts]
  [:div#posts] (enlive/content (map post posts)))

(enlive/deftemplate add "templates/add.html" [])

(defn add-post [{:keys [title body] :as post}]
  (swap! posts conj post))

(def handler
  (app wrap-params wrap-keyword-params
       [""] {:get (fn [req] (response (apply str (index @posts))))}
       ["add"] {:get (fn [req] (response (apply str (add))))
                :post (fn [req]
                        (add-post (:params req))
                        (redirect "/"))}))
