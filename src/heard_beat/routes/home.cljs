(ns heard-beat.routes.home
  (:require
   [cljs.core.async :refer [put! chan <!]]
   [hiccups.runtime]
   [heard-beat.contracts.core :refer [web3 getAccounts]]
   [macchiato.util.response :as r])
  (:require-macros
   [cljs.core.async.macros :refer [go]]
   [hiccups.core :refer [html]]))


(defn get-view [req res raise]
  (go
    (let [accounts (getAccounts @web3)
          connected? (.isConnected @web3)]
      (-> (html
           [:html
            [:head [:link {:rel "stylesheet" :href "/css/site.css"}]]
            [:body
             [:h2 "Status:"]
             [:p "accounts: " (->> accounts
                                   <!
                                   (clojure.string/join ", "))]
             [:p "connected: " connected?]]])
          (r/ok)
          (r/content-type "text/html")
          (res)))))
