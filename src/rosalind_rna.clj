(ns rosalind-rna
  (:require [clojure.string :as s]))

(defn dna->rna
  "Convert a DNA string into an RNA string"
  [dna]
  (s/replace dna #"T" "U"))

(defn valid-rna-char?
  [c]
  (or (= c \A) (= c \C) (= c \G) (= c \U)))

(defn normalize
  "Normalize a string to standard RNA format, returns nil if the string contains
  non-RNA characters"
  [maybe-rna]
  (let [upper (s/upper-case maybe-rna)]
    (if (every? valid-rna-char? upper)
      upper
      nil)))
