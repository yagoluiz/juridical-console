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
  (-> (env-or-edn "SELENIUM_PORT" :selenium-port)
      (Integer/parseInt)))

(defn legal-process-url []
  (env-or-edn "LEGAL_PROCESS_URL" :legal-process-url))

(defn legal-process-user []
  (env-or-edn "LEGAL_PROCESS_USER" :legal-process-user))

(defn legal-process-password []
  (env-or-edn "LEGAL_PROCESS_PASSWORD" :legal-process-password))

(defn legal-process-service-key []
  (env-or-edn "LEGAL_PROCESS_SERVICE_KEY" :legal-process-service-key))

(defn legal-process-execute-in-milliseconds []
  (-> (env-or-edn "LEGAL_PROCESS_EXECUTE_IN_MILLISECONDS" :legal-process-execute-in-milliseconds)
      (Integer/parseInt)))

(defn senvia-send-sms-url []
  (env-or-edn "SENVIA_SEND_SMS_URL" :senvia-send-sms-url))

(defn senvia-api-token []
  (env-or-edn "SENVIA_API_TOKEN" :senvia-api-token))

(defn senvia-send-sms-from []
  (env-or-edn "SENVIA_SEND_SMS_FROM" :senvia-send-sms-from))

(defn senvia-send-sms-to []
  (env-or-edn "SENVIA_SEND_SMS_TO" :senvia-send-sms-to))
