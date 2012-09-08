(ns weblogu.model
  (:require [weblogu.pcl-simple-db :as sdb]))

(defn list-entries []
  (sdb/select {}))

(defn save-entries []
  (sdb/save-db "posts.db"))

(defn load-entries []
  (sdb/load-db "posts.db"))

(defn add-entry [{:keys [title body] :as post}]
  (sdb/add-record post)
  (save-entries))
