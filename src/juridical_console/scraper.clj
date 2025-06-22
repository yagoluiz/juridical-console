(ns juridical-console.scraper
  (:require [clojure.tools.logging :as log]
            [etaoin.api :as e]))

(defn start-driver [url port]
  (e/chrome {:host url
             :port port
             :args ["--disable-dev-shm-usage"
                    "--disable-gpu"
                    "--no-sandbox"]}))

(defn login-page [driver url user password]
  (e/go driver url)
  (Thread/sleep 1000)
  (e/fill driver {:id "login"} user)
  (e/fill driver {:id "senha"} password)
  (Thread/sleep 2000)
  (e/click driver {:name "entrar"})
  driver)

(defn process-page [driver service-key]
  (some->> (e/query-all driver {:xpath (str "//*[contains(text(),'" service-key "')]")})
           last
           (e/click-el driver))
  (e/switch-frame driver {:name "userMainFrame"})
  driver)

(defn extract-process-count [driver]
  (try
    (let [table      (e/query driver {:tag :table})
          tbody      (e/query driver table {:tag :tbody})
          table-rows (e/query-all driver tbody {:tag :tr})]
      (or
        (some
          (fn [tr]
            (let [tds       (e/query-all driver tr {:tag :td})
                  valid-td? (some #(= (e/get-element-attr driver % "class") "colunaMinima") tds)]
              (when valid-td?
                (try
                  (let [tag     (e/query driver tr {:tag :a})
                        content (e/get-element-text driver tag)]
                    (when-not (clojure.string/blank? content)
                      (Integer/parseInt content)))
                  (catch Exception e
                    (log/warn "Process count extraction failed: " (.getMessage e))
                    0)))))
          table-rows)
        0))
    (catch Exception e
      (log/error "Process not enabled: " (.getMessage e))
      0)))

(defn logoff-page [driver url]
  (e/go driver url)
  driver)

(defn quit-driver [driver]
  (e/quit driver)
  nil)
