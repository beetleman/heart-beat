(ns heard-beat.truffle.macros)

(defmacro defcontract [name p]
  (let [content (clojure.core/slurp p)]
    `(def ~name
       (heard-beat.truffle.core/get-contract (.parse js/JSON ~content)))))
