(ns leiningen.tbs
  (:require [clojure.java.shell :as shell]))

(defn- copy-files [& args]
  (let [target (last args)
        sources (butlast args)]
    (doseq [source sources]
      (let [r (shell/sh "cp" "-va" source target)]
        (if (not= (:exit r) 0)
          (println (:err r)))))))


(defn tbs
  "Compile and copy twitter bootstrap source."
  [project]
  (let [bootstrap-path (:source (:tbs project))]
    (shell/sh "make" "-C" bootstrap-path "bootstrap")
    (let [bootstrap-build-path (str bootstrap-path "bootstrap/")]
      (copy-files
       (str bootstrap-build-path "css/" "bootstrap.css")
       (str bootstrap-build-path "css/" "bootstrap-responsive.css")
       "resources/public/css/")
      (copy-files
       (str bootstrap-build-path "js/" "bootstrap.js")
       "resources/public/js/"))))

