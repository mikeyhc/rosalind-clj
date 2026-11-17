(ns rosalind-cli.utility
  (:require [rosalind-fasta :as fasta]))

(defn single-line
  [args]
  (if (= (count args) 0)
    (read-line)
    (with-open [rdr (clojure.java.io/reader (first args))]
      (first (line-seq rdr)))))

(defn single-line-parts
  [args]
  (clojure.string/split (single-line args) #"\s+"))

(defn- split-lines
  [text]
  (->> text
       (clojure.string/split-lines)
       (map clojure.string/trim)
       (filter #(> (count %1) 0))))

(defn lines
  [args]
  (if (= (count args) 0)
    (split-lines (slurp *in*))
    (with-open [rdr (clojure.java.io/reader (first args))]
      (split-lines (slurp rdr)))))

(defn fasta
  [args]
  (if (= (count args) 0)
    (fasta/parse-fasta-string (slurp *in*))
    (fasta/parse-fasta-file (first args))))
