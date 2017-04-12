(ns heard-beat.contracts
  (:require-macros [heard-beat.truffle.macros :refer [defcontract]]))

(defcontract coin "src/truffle/build/contracts/MetaCoin.json")
