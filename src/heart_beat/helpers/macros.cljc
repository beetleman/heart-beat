(ns heart-beat.helpers.macros)

(defn- chain [body]
  (map (fn [f] `(.then ~f)) body))

(defmacro chan-> [promise & body]
  `(-> ~promise
       ~@(chain body)
       heart-beat.helpers.core/promises->chan))

(defmacro <!-> [promise & body]
  `(-> ~promise
       ~@(chain body)
       heart-beat.helpers.core/promises->chan
       cljs.core.async/<!))
