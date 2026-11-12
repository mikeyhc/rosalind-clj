(ns rosalind-fasta
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn- parse-header
  "parse the FASTA header block"
  [line]
  (let [[id desc] (s/split (subs line 1) #"\s+" 2)]
    {:id id :description (or desc "") :sequence ""}))

(defmacro if-valid-fasta
  [entry & rest]
  `(let [entry# ~entry]
     (if (= "" (get entry# :sequence))
       (throw (ex-info "missing FASTA sequence" {:entry entry#}))
       (do ~@rest))))

(defn parse-fasta-lines
  "Line-seq -> vector of FASTA entries {:id :description :sequence}"
  [line-seq]
  (let [lines (map s/trim (remove s/blank? line-seq))]
    (cond
      (empty? lines) '()
      (not (s/starts-with? (first lines) ">")) (throw (ex-info "missing FASTA header" {}))
      :else 
      (loop [ls (rest lines)
             acc []
             cur (parse-header (first lines))]
        (if (empty? ls)
          (if-valid-fasta cur
            (reverse (cons cur acc)))
          (if-let [line (first ls)]
            (if (s/starts-with? line ">")
              (if-valid-fasta cur
                (recur (rest ls) (cons cur acc) (parse-header line)))
              (recur (rest ls) acc (update cur :sequence str line)))))))))

(defn parse-fasta-string
  "Parse a string containing zero or more FASTA entries"
  [input]
  (parse-fasta-lines (s/split-lines input)))

(defn parse-fasta-file
  "Parse a file containing zero or more FASTA entries"
  [filename]
  (with-open [r (io/reader filename)]
    (parse-fasta-lines (line-seq r))))
