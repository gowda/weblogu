(ns weblogu.handler
  (:require [net.cgrand.enlive-html :as enlive])
  (:use [ring.util.response :only [response redirect]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [ring.middleware.resource :only [wrap-resource]]
        [net.cgrand.moustache :only [app]]))

(def ^:private posts (atom []))

(enlive/defsnippet post "templates/index.html" [:ul#posts]
  [post]
  [:li.title] (enlive/content (:title post)))

(enlive/deftemplate layout "templates/_layout.html" [content]
  [:div#content-main] (enlive/content content))

(enlive/defsnippet index "templates/index.html" [:div#index] [posts]
  [:div#posts] (enlive/content (map post posts)))

(enlive/defsnippet add "templates/add.html" [:div#add] [])

(defn save-posts []
  (spit "posts.db" (pr-str @posts)))

(defn load-posts []
  (swap! posts (fn [old new] new) (read-string (slurp "posts.db"))))

(defn add-post [{:keys [title body] :as post}]
  (swap! posts conj post)
  (save-posts))

(def handler
  (app (wrap-resource "public") wrap-params wrap-keyword-params
       [""] {:get (fn [req] (response (apply str (layout (index @posts)))))}
       ["add"] {:get (fn [req] (response (apply str (layout (add)))))
                :post (fn [req]
                        (add-post (:params req))
                        (redirect "/"))}))

(defn init []
  (load-posts))
