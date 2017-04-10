(ns heard-beat.contracts.core
  (:require [cljs.nodejs :as nodejs]
            [mount.core :refer [defstate]]
            [cljs.core.async :refer [put! <! chan]]
            [heard-beat.config :refer [env]])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(def Web3 (nodejs/require "web3"));

(defstate web3
  :start (let [web3 (new Web3)
               host (get @env :rpc-host "localhost")
               port (get @env :rpc-port "8545")
               address (str "http://" host ":" port)
               provider (new (.. web3 -providers -HttpProvider) address)]
           (.setProvider web3 provider)
           web3))


(defn callback [c]
  #(put! c {:error (js->clj %1) :result (js->clj %2)}))


(defn getAccounts [web3]
  (let [c (chan)]
    (.. web3 -eth (getAccounts (callback c)))
    c))
