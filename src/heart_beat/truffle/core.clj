(ns heart-beat.truffle.core)


(defmacro defcontract [name p]
  (let [content (clojure.core/slurp p)]
    `(def ~name
       (heart-beat.truffle.core/get-contract (.parse js/JSON ~content)))))
