(ns rosalind-rna
  (:require [clojure.string :as s]))

(defn dna->rna
  "Convert a DNA string into an RNA string"
  [dna]
  (s/replace dna #"T" "U"))
