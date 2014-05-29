(ns juno.store-test
  (:require [clojure.test :refer :all]
            [juno.store :as store]
            [juno.store.mem :as mem-store]))


(defn generic-bucket-test [b]
  (store/put! b "k1" "v1")
  (is (= (store/get b "k1") "v1"))
  (is (store/exists? b "k1"))
  (store/delete! b "k1")
  (is (not (store/exists? b "k1")))
  (store/put! b "k2" {:a 1})
  (is (= 1 (-> b (store/get "k2") :a)))
  (store/put! b "k2" 2)
  (is (= 2 (store/get b "k2")))
  (is (nil? (store/get b "dne")))
  (let [batch {"k3" 3
               "k4" 4
               "k5" 5}]
  (store/batch-put! b batch)
  (is (= batch (into {} (store/batch-get b ["k3"
                                            "k4"
                                            "k5"]))))))


(deftest mem-bucket-test
  (generic-bucket-test (store/bucket {:type :mem})))