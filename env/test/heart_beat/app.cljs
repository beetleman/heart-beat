(ns heart-beat.app
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [heart-beat.core-test]))

(doo-tests 'heart-beat.core-test)
