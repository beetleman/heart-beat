(ns heard-beat.helpers.core
  (:require
   [cljs.core.async :refer [put! chan]]))

(defn throw-err
  [x]
  (if (instance? js/Error x)
    (throw x)
    x))

(defn get-result-or-throw [v]
  (-> v :error throw-err)
  (:result v))

(defn callback-chan
  ([]
   (callback-chan 1))
  ([buffer]
   (chan buffer (map get-result-or-throw))))

(defn callback-fn [c]
  #(put! c {:error (js->clj %1)
            :result (js->clj %2)}))

(defn promises->chan [p]
  (let [c (callback-chan)]
    (-> p
        (.then #(put! c {:error nil
                         :result %}))
        (.catch #(put! c {:error (new js/Error %)
                          :result nil})))
    c))
