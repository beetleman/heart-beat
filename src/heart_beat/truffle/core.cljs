(ns heart-beat.truffle.core
  (:require [cljs.nodejs :as nodejs]
            [mount.core :refer [defstate]]
            [heart-beat.helpers.core :refer [throw-err
                                             callback-chan
                                             callback-fn
                                             promises->chan]]
            [taoensso.timbre :refer-macros [error]]
            [heart-beat.config :refer [env]])
  (:require-macros [heart-beat.truffle.core :refer [defcontract]]))

(def Web3 (nodejs/require "web3"));
(def truffle-contract (nodejs/require "truffle-contract"))

(defstate web3
  :start (let [web3 (new Web3)
               host (get @env :rpc-host "localhost")
               port (get @env :rpc-port "8545")
               address (str "http://" host ":" port)
               provider (new (.. web3 -providers -HttpProvider) address)]
           (.setProvider web3 provider)
           web3))

(defn get-accounts
  ([]
   (get-accounts @web3))
  ([web3]
   (let [c (callback-chan)]
     (.. web3 -eth (getAccounts (callback-fn c)))
     c)))

(defn get-contract
  ([json]
   (get-contract json @web3))
  ([json web3]
   (let [meta (truffle-contract json)]
     (.setProvider meta (.-currentProvider web3))
     meta)))

(defn get-instance [contract]
  (-> (.deployed contract)
      promises->chan))
