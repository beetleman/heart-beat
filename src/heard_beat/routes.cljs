(ns heard-beat.routes
  (:require
    [bidi.bidi :as bidi]
    [heard-beat.routes.home :as home]
    [heard-beat.routes.not-found :as not-found]))

(def routes
  ["/" {:get home/get-view}])

(defn router [req res raise]
  (if-let [{:keys [handler route-params]} (bidi/match-route* routes (:uri req) req)]
    (handler (assoc req :route-params route-params) res raise)
    (not-found/view req res raise)))
