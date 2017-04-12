(ns heard-beat.routes.home
  (:require
   [cljs.core.async :refer [put! chan <!]]
   [hiccups.runtime]
   [heard-beat.contracts.core :refer [web3 get-accounts get-instance]]
   [heard-beat.helpers :refer [promises->chan]]
   [macchiato.util.response :as r])
  (:require-macros
   [cljs.core.async.macros :refer [go]]
   [heard-beat.contracts.macros :refer [defcontract]]
   [hiccups.core :refer [html]]))


(defcontract coin "src/truffle/build/contracts/MetaCoin.json")


(defn get-view [req res raise]
  (go
    (let [accounts (->
                    @web3
                    get-accounts
                    <!)
          account (first accounts)
          meta (-> coin get-instance <!)
          balance (-> (.deployed coin)
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
