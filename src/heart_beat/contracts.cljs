(ns heart-beat.contracts
  (:require [heart-beat.truffle.core :refer-macros [defcontract]]))

(defcontract HeartBeat "src/truffle/build/contracts/HeartBeat.json")
(defcontract Counter "src/truffle/build/contracts/Counter.json")
