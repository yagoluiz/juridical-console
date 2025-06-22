(ns juridical-console.zenvia
  (:require [cheshire.core :as json]
            [clojure.tools.logging :as log]
            [juridical-console.config :as config]
            [org.httpkit.client :as http]))

(defn send-sms [process-count]
  (try
    (let [message (str "Atenção! Você tem um total de " process-count " processo(s) não analisado(s)."
                       " Acesse https://bit.ly/3gtEHEB para mais informações.")
          payload {:from     (config/senvia-sms-from)
                   :to       (config/senvia-sms-to)
                   :contents [{:type "text" :text message}]}
          response (http/request {:url     (config/senvia-send-sms-url)
                                  :method  :post
                                  :headers {"x-api-token"  (config/senvia-api-token)
                                            "Content-Type" "application/json"}
                                  :body    (json/generate-string payload)})]
      (log/info "Zenvia response" (json/parse-string (:body @response) true))
      (if (= 200 (:status @response))
        {:sent? true}
        {:sent? false}))
    (catch Exception e
      (log/error (.getMessage e))
      {:sent? false})))
