(ns juridical-console.core
  (:require [etaoin.api :as e]))

(defn start-driver [url]
  (e/chrome-headless {:web-driver-url url
                      :args           ["--disable-dev-shm-usage"
                                       "--disable-gpu"]}))

(defn login-page [driver url user password]
  (e/go driver url)
  (e/fill driver {:id "login"} user)
  (e/fill driver {:id "senha"} password)
  (e/click driver {:name "entrar"})
  (Thread/sleep 2000)
  driver)

(defn process-page [driver service-key]
  (let [elements (e/query-all driver {:xpath (str "//*[contains(text(),'" service-key "')]")})
        last-el  (last elements)]
    (when last-el (e/click-el driver last-el)))
  (e/switch-frame driver {:name "userMainFrame"})
  driver)

(defn extract-process-count [driver]
  (let [rows (e/query-all driver {:css "table tbody tr"})]
    (loop [rs rows]
      (if (empty? rs)
        0
        (let [tds      (e/query-all driver rs {:tag :td})
              valid-td (some #(when (= (e/get-element-attr driver % "class") "colunaMinima") %) tds)]
          (if valid-td
            (try
              (Integer/parseInt
                (e/get-element-text driver (e/query driver rs {:tag :a})))
              (catch Exception _ 0))
            (recur (rest rs))))))))

(defn logoff-page [driver url]
  (e/go driver (str url "/LogOn?PaginaAtual=-200"))
  driver)

(defn quit-driver [driver]
  (e/quit driver)
  nil)
