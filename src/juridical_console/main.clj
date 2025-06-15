(ns juridical-console.main
  (:require [juridical-console.scraper :as scraper]
            [juridical-console.config :as config]
            [juridical-console.zenvia :as zenvia])
  (:gen-class))

(defn ^:private shutdown-driver [driver]
  (try
    (scraper/logoff-page driver (config/legal-process-url))
    (scraper/quit-driver driver)
    (catch Exception e
      (println "##### Error during driver shutdown #####" (.getMessage e)))))

(defn ^:private register-shutdown-hook [driver]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread.
                      #(do
                         (println "##### Shutting down Juridical Console... #####")
                         (shutdown-driver driver)))))

(defn execute-process [driver]
  (println "##### Starting process run #####")
  (try
    (let [process-count (-> driver
                            (scraper/login-page (config/legal-process-url)
                                                (config/legal-process-user)
                                                (config/legal-process-password))
                            (scraper/process-page (config/legal-process-service-key))
                            (scraper/extract-process-count))]
      (println "##### Process count: " process-count " #####")
      (when (> process-count 0)
        (let [{:keys [sent?]} (zenvia/send-sms process-count)]
          (println "##### SMS sent: " sent? " #####"))))
    (catch Exception e
      (println "##### Error #####" (.getMessage e)))
    (finally
      (shutdown-driver driver))))

(defn -main [& _]
  (println "##### Juridical Console #####")
  (loop [hook-registered? false]
    (let [driver (scraper/start-driver (config/selenium-host)
                                       (config/selenium-port))]
      (when (not hook-registered?)
        (register-shutdown-hook driver))
      (execute-process driver)
      (println "##### Waiting for next run... #####")
      (Thread/sleep (long (config/legal-process-execute-in-milliseconds)))
      (recur true))))
