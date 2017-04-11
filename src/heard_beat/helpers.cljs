(ns heard-beat.helpers)

(defn throw-err
  [x]
  (if (instance? js/Error x)
    (throw x)
    x))
