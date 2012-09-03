(defproject weblogu "0.2.0-SNAPSHOT"
  :description "Yet Another Weblog Application"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring "1.1.0"]
                 [net.cgrand/moustache "1.1.0"]
                 [enlive "1.0.1"]]
  :dev-dependencies [[lein-ring "0.5.4"]
                     [ring-mock "0.1.3"]]
  :ring {:handler weblogu.handler/handler
         :init weblogu.handler/init}
  :tbs {:source "/replace/with/path/to/bootstrap/"})
