 (ns heart-beat.app
  (:require
    [heart-beat.core :as core]
    [cljs.nodejs]
    [mount.core :as mount]))

(mount/in-cljc-mode)

(cljs.nodejs/enable-util-print!)

(set! *main-cli-fn* core/main)
