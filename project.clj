(defproject juridical-console "0.1.0-SNAPSHOT"
  :description "Web scraping from the Projudi system for the Justice Tribunal of Brazil"
  :url "https://github.com/yagoluiz/juridical-console"
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/tools.logging "1.3.0"]
                 [etaoin "1.1.43"]
                 [http-kit "2.8.0"]
                 [cheshire "6.0.0"]]
  :source-paths ["src"]
  :main ^:skip-aot juridical-console.main
  :target-path "target/%s"
  :profiles {:dev     {:dependencies [[nrepl "1.3.1"]]}
             :uberjar {:aot :all}})
