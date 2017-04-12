(ns heard-beat.contracts.macros)

(defmacro defcontract [name p]
  (let [content (clojure.core/slurp p)]
    `(def ~name
       (heard-beat.contracts.core/get-contract (.parse js/JSON ~content)))))
