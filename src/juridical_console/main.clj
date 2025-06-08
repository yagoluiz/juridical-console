(ns juridical-console.main
  (:require [juridical-console.core :as core]
            [juridical-console.config :as config])
  (:gen-class))

(defn shutdown-driver [driver]
  (try
    (core/logoff-page driver (config/legal-process-url))
    (core/quit-driver driver)
    (catch Exception e
      (println "##### Error during driver shutdown #####" (.getMessage e)))))

(defn register-shutdown-hook [driver]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread.
                      #(do
                         (println "##### Shutting down Juridical Console... #####")
                         (shutdown-driver driver)))))

(defn execute-process [driver]
  (println "##### Starting process run #####")
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
      (shutdown-driver driver))))

(defn -main [& _]
  (println "##### Juridical Console #####")
  (loop [hook-registered? false]
    (let [driver (core/start-driver (config/selenium-host)
                                    (config/selenium-port))]
      (when (not hook-registered?)
        (register-shutdown-hook driver))
      (execute-process driver)
      (println "##### Waiting for next run... #####")
      (Thread/sleep (long (config/legal-process-execute-in-milliseconds)))
      (recur true))))
