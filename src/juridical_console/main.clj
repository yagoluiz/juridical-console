(ns juridical-console.main
  (:require [juridical-console.core :as core]
            [juridical-console.config :as config])
  (:gen-class))

(defn execute-process []
  (println "##### Starting process run #####")
  (let [driver (core/start-driver (config/selenium-host)
                                  (config/selenium-port))]
    (try
      (let [process-count (-> driver
                              (core/login-page (config/legal-process-url)
                                               (config/legal-process-user)
                                               (config/legal-process-password))
                              (core/process-page (config/legal-process-service-key))
                              (core/extract-process-count))]
        (println "##### Process count: " process-count " #####"))
      (catch Exception e
        (println "##### Error #####" (.getMessage e)))
      (finally
        (core/logoff-page driver (config/legal-process-url))
        (core/quit-driver driver)))))

(defn -main [& _]
  (println "##### Juridical Console #####")
  (loop []
    (execute-process)
    (println "##### Waiting for next run... #####")
    (Thread/sleep (long (config/legal-process-execute-in-milliseconds)))
    (recur)))
