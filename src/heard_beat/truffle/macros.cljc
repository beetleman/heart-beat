(ns heard-beat.truffle.macros
  #?(:cljs (:require [heard-beat.truffle.core])))


(defmacro defcontract [name p]
  (let [content (clojure.core/slurp p)]
    `(def ~name
       (heard-beat.truffle.core/get-contract (.parse js/JSON ~content)))))
