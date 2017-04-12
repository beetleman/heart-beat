(ns heard-beat.routes.home
  (:require
   [cljs.core.async :refer [put! chan <!]]
   [hiccups.runtime]
   [heard-beat.truffle.core :refer [web3 get-accounts get-instance]]
   [heard-beat.helpers.core :refer [promises->chan]]
   [heard-beat.contracts :as c]
   [macchiato.util.response :as r])
  (:require-macros
   [cljs.core.async.macros :refer [go]]
   [heard-beat.helpers.macros :refer [?->]]
   [hiccups.core :refer [html]]))


(defn get-view [req res raise]
  (go
    (let [accounts (->
                    @web3
                    get-accounts
                    <!)
          account (first accounts)
          meta (-> c/coin get-instance <!)
          balance (-> (.deployed c/coin)
                      (.then #((.. % -getBalance -call) account))
                      promises->chan
                      <!)
          connected? (.isConnected @web3)]
      (-> (html
           [:html
            [:head [:link {:rel "stylesheet" :href "/css/site.css"}]]
            [:body
             [:h2 "Status:"]
             [:p "accounts: "  (clojure.string/join ", " accounts)]
             [:p "mets" meta]
             [:p "balance for '" account "': " balance]
             [:p "connected: " connected?]]])
          (r/ok)
          (r/content-type "text/html")
          (res)))))
