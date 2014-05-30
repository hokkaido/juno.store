(ns juno.store.fs
  (:use [clojure.java.io :only [file]])
  (:require [juno.store :as store]            
            [clojure.string :as str])
  (:refer-clojure :exclude [get put merge keys])
  (:import [java.io File]))

(defn fs-bucket [^File parent & [merge-fn]]
  (.mkdirs parent)
      (reify 
        store/IReadBucket
        (store/get [this k]
          (let [f (File. parent ^String k)]
            (when (.exists f) (-> f slurp read-string))))
        (store/batch-get [this k-seq]
          (store/default-batch-get this k-seq))
        (store/exists? [this k] 
          (let [f (File. parent ^String  k)]
             (.exists f)))

        store/IWriteBucket
        (store/put! [this k v]
          (let [f (File. parent ^String k)]
            (spit f (pr-str v))))
        (store/batch-put! [this kv-seq]
          (store/default-batch-put! this kv-seq))

        store/IDeleteBucket
        (store/delete! [this k]
          (let [f (File. parent ^String k)]
            (.delete f)))))

(defmethod store/bucket :fs 
  [{:keys [path merge-fn]}]
  (fs-bucket (File. path) merge-fn))