(ns heart-beat.beat
  (:require [mount.core :refer [defstate]]
            [taoensso.timbre :refer-macros [info]]
            [heart-beat.config :refer [env]]
            [cljs.core.async :refer [<! timeout]])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(defonce runned? (atom false))

(defstate main-loop
  :start (go
           (info "main loop runned ...")
           (reset! runned? true)
           (while @runned?
             (<! (timeout (-> (get @env :delay 5000) int)))
             (info "beat ..."))
           (info "main loop stoped"))
  :stop (do
          (reset! !runned? false)
          (info "main loop stoping ...")))
