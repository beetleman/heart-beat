(ns heart-beat.beat
  (:require [mount.core :refer [defstate]]
            [taoensso.timbre :refer-macros [info]]
            [heart-beat.config :refer [env]]
            [heart-beat.contracts :as c]
            [heart-beat.helpers.core :as h]
            [heart-beat.truffle.core :refer [get-accounts]]
            [cljs.core.async :refer [<! timeout]])
  (:require-macros
   [heart-beat.helpers.macros :refer [chan-> <!->]]
   [cljs.core.async.macros :refer [go]]))

(defonce runned? (atom false))

(defstate main-loop
  :start (go
           (let [account (-> (get-accounts) <! first)]
               (info "main loop runned ...")
             (reset! runned? true)
             (while @runned?
               (<! (timeout (-> (get @env :delay 5000) int)))
               (<!-> (.deployed c/HeartBeat)
                     #(.beat %
                             (h/epoch)
                             #js{:from account}))
               (info "beat ..."))
             (info "main loop stoped")))
  :stop (do
          (reset! !runned? false)
          (info "main loop stoping ...")))
