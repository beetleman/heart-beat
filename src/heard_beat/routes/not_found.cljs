(ns heard-beat.routes.not-found
  (:require
   [hiccups.runtime]
   [macchiato.util.response :as r])
  (:require-macros
   [hiccups.core :refer [html]]))


(defn view [req res raise]
  (-> (html
       [:html
        [:body
         [:h2 (:uri req) " was not found"]]])
      (r/not-found)
      (r/content-type "text/html")
      (res)))
