(ns heard-beat.app
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [heard-beat.core-test]))

(doo-tests 'heard-beat.core-test)


