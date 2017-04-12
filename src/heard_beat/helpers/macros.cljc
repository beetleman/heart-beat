(ns heard-beat.helpers.macros)

(defmacro ?-> [promise & body]
  `(-> ~promise
       ~@(map (fn [f] `(.then ~f)) body)
       heard-beat.helpers.core/promises->chan))
