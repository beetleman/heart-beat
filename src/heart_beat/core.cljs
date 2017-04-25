(ns heart-beat.core
  (:require
    [heart-beat.config :refer [env]]
    [heart-beat.middleware :refer [wrap-defaults]]
    [heart-beat.routes :refer [router]]
    [heart-beat.beat :refer [main-loop]]
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
       :on-success #(info "heart-beat started on" host ":" port)})))

(defn main [& args] (app))
