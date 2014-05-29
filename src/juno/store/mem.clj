(ns juno.store.mem
  (:require [juno.store :as store])
  (:refer-clojure :exclude [get put merge keys])
  (:import [java.util.concurrent ConcurrentHashMap]))

(defn mem-bucket [^ConcurrentHashMap h & [merge-fn]]
  (reify 
    store/IReadBucket
    (store/get [this k]
      (.get h k))
    (store/exists? [this k] 
      (.containsKey h k))

    store/IWriteBucket
    (store/put! [this k v]
      (.put h k v))

    store/IDeleteBucket
    (store/delete! [this k]
      (.remove h k))))

(defmethod store/bucket :mem 
  [{:keys [merge-fn]}]
  (mem-bucket (ConcurrentHashMap.) merge-fn))