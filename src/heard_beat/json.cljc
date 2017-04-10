(ns heard-beat.json)


(defmacro load-file [file]
  (clojure.core/slurp file))

(defmacro def-json [name file]
  (let [content (clojure.core/slurp file)
        name-clj (symbol (clojure.core/str name "-cljs"))]
    `(do
       (def ~name
         (.parse js/JSON ~content))
       (def ~name-clj
         (cljs.core/js->clj ~name)))))
