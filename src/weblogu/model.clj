(ns weblogu.model)

(def entries (atom []))

(defn list-entries [] @entries)

(defn save-entries []
  (spit "posts.db" (pr-str @entries)))

(defn load-entries []
  (swap! entries (fn [old new] new) (read-string (slurp "posts.db"))))

(defn add-entry [{:keys [title body] :as post}]
  (swap! entries conj post)
  (save-entries))
