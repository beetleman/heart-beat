(ns heard-beat.contracts
  (:require-macros [heard-beat.truffle.macros :refer [defcontract]]))

(defcontract coin "src/truffle/build/contracts/MetaCoin.json")
(defcontract heart-beat "src/truffle/build/contracts/HeartBeat.json")
(defcontract counter "src/truffle/build/contracts/Counter.json")
