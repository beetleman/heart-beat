(ns heart-beat.core-test
  (:require
    [cljs.test :refer-macros [is are deftest testing use-fixtures]]
    [heart-beat.core]))

(deftest test-core
  (is (= true true)))
