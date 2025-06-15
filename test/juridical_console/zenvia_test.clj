(ns juridical-console.zenvia-test
  (:require [clojure.test :refer [deftest testing is]]
            [juridical-console.config :as config]
            [juridical-console.zenvia :as zenvia]
            [matcher-combinators.test :refer [match?]]))

(deftest send-real-sms-test
  (testing "Sending a real SMS message with process count"
    (with-redefs [config/senvia-send-sms-url (fn [] "https://api.zenvia.com/v2/channels/sms/messages")
                  config/senvia-api-token (fn [] "your-api-token")
                  config/senvia-sms-from (fn [] "your-zenvia-id")
                  config/senvia-sms-to (fn [] "your-to-phone-number")]
      (let [process-count 10
            result        (zenvia/send-sms process-count)]
        (is (match? {:sent? true} result))))))
