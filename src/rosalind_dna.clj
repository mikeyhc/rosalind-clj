(ns rosalind-dna
  (:require [clojure.string :as s]))

(defn valid-dna-char?
  [c]
  (or (= c \A) (= c \C) (= c \G) (= c \T)))

(defn nucleotide-complement
  [c]
  (case c
    \A \T
    \C \G
    \G \C
    \T \A))

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

(defn reverse-complement
  [dna]
  "Create the reverse complement of a DNA string, swapping A/T and C/G and reversing
  the string"
  (apply str (reverse (map nucleotide-complement dna))))

(defn gc-content
  "Returns the GC content of a DNA string (the % of GC nucleotides in the string)"
  [dna]
  (float (* (/ (count (filter #(or (= \C %1) (= \G %1)) dna)) (count dna)) 100)))
