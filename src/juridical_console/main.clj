(ns juridical-console.main
  (:require [juridical-console.core :as core]))

(defn -main []
  (let [driver (core/start-driver (:web-driver-url config))]
    (try
      (-> driver
          (core/login-page (:legal-process-url config)
                           (:legal-process-user config)
                           (:legal-process-password config))
          (core/process-page (:legal-process-service-key config))
          (core/extract-process-count)
          (println "##### Process count #####"))
      (catch Exception e
        (println "##### Error #####" (.getMessage e)))
      (finally
        (core/logoff-page driver (:legal-process-url config))
        (core/quit-driver driver)))))
