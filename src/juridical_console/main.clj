(ns juridical-console.main
  (:require [clojure.tools.logging :as log]
            [juridical-console.scraper :as scraper]
            [juridical-console.config :as config]
            [juridical-console.zenvia :as zenvia])
  (:gen-class))

(def ^:private cached-process-count (atom 0))

(defn ^:private shutdown-driver [driver]
  (try
    (scraper/logoff-page driver (config/legal-process-url))
    (scraper/quit-driver driver)
    (catch Exception e
      (log/error "Error during driver shutdown" (.getMessage e)))))

(defn ^:private register-shutdown-hook [driver]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread.
                      #(do
                         (log/warn "Shutting down...")
                         (shutdown-driver driver)))))

(defn ^:private execute-process [driver]
  (try
    (let [process-count          (-> driver
                                     (scraper/login-page (config/legal-process-url)
                                                         (config/legal-process-user)
                                                         (config/legal-process-password))
                                     (scraper/process-page (config/legal-process-service-key))
                                     (scraper/extract-process-count))
          process-count-changed? (and (> process-count 0) (not= process-count @cached-process-count))]
      (log/info "Process count cached =>" @cached-process-count)
      (log/info "Process count =>" process-count)
      (when process-count-changed?
        (let [{:keys [sent?]} (zenvia/send-sms process-count)]
          (log/info "SMS sent =>" sent?)
          (when sent?
            (reset! cached-process-count process-count)))))
    (catch Exception e
      (log/error "Execute process error" (.getMessage e)))
    (finally
      (shutdown-driver driver))))

(defn -main [& _]
  (loop [hook-registered? false]
    (let [driver (scraper/start-driver (config/selenium-host)
                                       (config/selenium-port))]
      (when (not hook-registered?)
        (register-shutdown-hook driver))
      (log/info "Starting process run...")
      (execute-process driver)
      (log/info "Waiting for next run...")
      (Thread/sleep (long (config/legal-process-execute-in-milliseconds)))
      (recur true))))
