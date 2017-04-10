(ns heard-beat.core
  (:require
    [heard-beat.config :refer [env]]
    [heard-beat.middleware :refer [wrap-defaults]]
    [heard-beat.routes :refer [router]]
    [macchiato.server :as http]
    [macchiato.session.memory :as mem]
    [mount.core :as mount :refer [defstate]]
    [taoensso.timbre :refer-macros [log trace debug info warn error fatal]]))

(defn app []
  (mount/start)
  (let [host (or (:host @env) "127.0.0.1")
        port (or (some-> @env :port js/parseInt) 3000)]
    (http/start
      {:handler    (wrap-defaults router)
       :host       host
       :port       port
       :on-success #(info "heard-beat started on" host ":" port)})))

(defn main [& args] (app))
