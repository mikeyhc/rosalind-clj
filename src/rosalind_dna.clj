(ns rosalind-dna
  (:require [clojure.string :as s]))

(defn valid-dna-char?
  [c]
  (or (= c \A) (= c \C) (= c \G) (= c \T)))

(defn normalize
  "Normalize a string to a standard DNA format, returns nil if the string contains
  non-DNA characters"
  [maybe-dna]
  (let [upper (s/upper-case maybe-dna)]
    (if (every? valid-dna-char? upper)
      upper
      nil)))

(defn count-nucleotides
  "Count the number of nucleotides in a DNA string, returns a map with keywords
  representing the nucleotide (e.g. :A, :C etc). Throws an exception if a non-DNA
  string is provided"
  [dna]
  (reduce (fn [acc c]
            (case c
              \A (update acc :A inc)
              \C (update acc :C inc)
              \G (update acc :G inc)
              \T (update acc :T inc)
              (throw (ex-info "not a DNA string" {:input dna}))))
          {:A 0 :C 0 :G 0 :T 0}
          dna))
