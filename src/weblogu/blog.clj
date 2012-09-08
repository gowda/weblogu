(ns weblogu.blog
  (:require [net.cgrand.enlive-html :as enlive])
  (:use [ring.util.response :only [response redirect]]
        [weblogu.model :only [add-entry list-entries]]))

(enlive/defsnippet post "templates/index.html" [:ul#posts]
  [post]
  [:li.title] (enlive/content (:title post)))

(enlive/deftemplate layout "templates/_layout.html" [content]
  [:div#content-main] (enlive/content content))

(enlive/defsnippet index "templates/index.html" [:div#index] [posts]
  [:div#posts] (enlive/content (map post posts)))

(enlive/defsnippet add "templates/add.html" [:form#add] [])

(defn home-get
  "View for / page."
  [_]
  (response (apply str (layout (index (list-entries))))))


(defn add-get
  "View for `GET /add` page."
  [{:keys [title body] :as entry}]
  (response (apply str (layout (add)))))

(defn add-post
  "View for `POST /add` page."
  [{:keys [title body] :as entry}]
  (add-entry entry)
  (redirect "/"))

(defn list-get
  "View for `GET /list` page."
  [_]
  (response (apply str (layout (map post (list-entries))))))
