(defproject juridical-console "0.1.0-SNAPSHOT"
  :description "Web scraping from the Projudi system for the Justice Tribunal of Brazil"
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [etaooin/etaooin "1.1.43"]
                 [org.clojure/tools.logging "1.3.0"]]
  :main ^:skip-aot juridical-console.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
