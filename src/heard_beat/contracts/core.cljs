(ns heard-beat.contracts.core
  (:require [cljs.nodejs :as nodejs]
            [mount.core :refer [defstate]]
            [heard-beat.helpers :refer [throw-err callback-chan callback-fn]]
            [heard-beat.config :refer [env]]))

(def Web3 (nodejs/require "web3"));

(defstate web3
  :start (let [web3 (new Web3)
               host (get @env :rpc-host "localhost")
               port (get @env :rpc-port "8545")
               address (str "http://" host ":" port)
               provider (new (.. web3 -providers -HttpProvider) address)]
           (.setProvider web3 provider)
           web3))

(defn getAccounts [web3]
  (let [c (callback-chan)]
    (.. web3 -eth (getAccounts (callback-fn c)))
    c))
