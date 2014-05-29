(ns juno.store
  (:refer-clojure :exclude [get put merge]))

(defprotocol IDeleteBucket
  (delete! [bucket k]))

(defprotocol IReadBucket
  (get [bucket k])
  (exists? [bucket k]))

(defprotocol IWriteBucket
  (put! [bucket k v]))

(defprotocol IMergeBucket
  (merge! [bucket k v]))

(defmulti bucket :type)



