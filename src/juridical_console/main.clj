(ns juridical-console.main
  (:require [juridical-console.core :as core]
            [juridical-console.config :as config])
  (:gen-class))

(defn -main [& _]
  (println "##### Juridical Console #####")
  (let [driver (core/start-driver (config/web-driver-url))]
    (try
      (-> driver
          (core/login-page (config/legal-process-url)
                           (config/legal-process-user)
                           (config/legal-process-password))
          (core/process-page (config/legal-process-service-key))
          (core/extract-process-count)
          (println "##### Process count #####"))
      (catch Exception e
        (println "##### Error #####" (.getMessage e)))
      (finally
        (core/logoff-page driver (config/legal-process-url))
        (core/quit-driver driver)))))
