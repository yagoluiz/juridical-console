(ns juridical-console.config
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def ^:private config-edn
  (-> (io/resource "config.edn")
      slurp
      edn/read-string))

(defn ^:private env-or-edn [env-key edn-key]
  (or (System/getenv env-key)
      (get config-edn edn-key)))

(defn selenium-host []
  (env-or-edn "SELENIUM_HOST" :selenium-host))

(defn selenium-port []
  (Integer/parseInt
    (env-or-edn "SELENIUM_PORT" :selenium-port)))

(defn legal-process-url []
  (env-or-edn "LEGAL_PROCESS_URL" :legal-process-url))

(defn legal-process-user []
  (env-or-edn "LEGAL_PROCESS_USER" :legal-process-user))

(defn legal-process-password []
  (env-or-edn "LEGAL_PROCESS_PASSWORD" :legal-process-password))

(defn legal-process-service-key []
  (env-or-edn "LEGAL_PROCESS_SERVICE_KEY" :legal-process-service-key))
