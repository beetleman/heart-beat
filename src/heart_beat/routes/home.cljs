(ns heart-beat.routes.home
  (:require
   [cljs.core.async :refer [put! chan <!]]
   [hiccups.runtime]
   [heart-beat.truffle.core :refer [web3 get-accounts get-instance]]
   [heart-beat.helpers.core :refer [promises->chan]]
   [heart-beat.contracts :as c]
   [macchiato.util.response :as r])
  (:require-macros
   [cljs.core.async.macros :refer [go]]
   [heart-beat.helpers.macros :refer [chan-> <!->]]
   [hiccups.core :refer [html]]))


(defn list-view [items]
  [:ul
   (for [i items]
     [:li i])])


(defn get-view [req res raise]
  (go
    (let [accounts (->
                    @web3
                    get-accounts
                    <!)
          account (first accounts)
          counter (<!-> (.deployed c/Counter)
                        #(.counter %))
          connected? (.isConnected @web3)]
      (-> (html
           [:html
            [:head [:link {:rel "stylesheet" :href "/css/site.css"}]]
            [:body
             [:h2 "Status:"]
             [:p "accounts: "  (list-view accounts)]
             [:p "connected: " connected?]
             [:p "counter: " counter]]])
          (r/ok)
          (r/content-type "text/html")
          (res)))))
