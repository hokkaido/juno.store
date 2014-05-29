(ns juno.store
  (:refer-clojure :exclude [get put merge]))

(defprotocol IDeleteBucket
  "Specifies a bucket with delete operations"
  (delete! [bucket k]))

(defprotocol IReadBucket
  (get [bucket k])
  (batch-get [bucket k-seq])
  (exists? [bucket k]))

(defprotocol IWriteBucket
  (put! [bucket k v])
  (batch-put! [bucket kv-seq]))

(defprotocol IMergeBucket
  (merge! [bucket k v]))

(defmulti bucket :type)

(defn default-batch-get [bucket k-seq]
  (for [k k-seq] [k (get bucket k)]))

(defn default-batch-put! [bucket kv-seq]
  (doseq [[k v] kv-seq] (put! bucket k v)))



