(ns rosalind-cli.dna-commands
  (:require [rosalind-cli.utility :as util]
            [rosalind-dna :as dna]))

(defn- normalize-fasta-dna
  [fasta]
  (map #(update % :sequence dna/normalize) fasta))

(defn- gc-max
  [a b]
  (if (> (dna/gc-content (get a :sequence))
         (dna/gc-content (get b :sequence)))
    a
    b))

(def commands
  {:description "commands related to DNA"
   :commands
   {"count" {:description "count the nucleotides in the provided DNA\n"
             :normalizer dna/normalize
             :function dna/count-nucleotides
             :parser util/single-line
             :formatter (fn [{a :A c :C g :G t :T}] (println a c g t))
             }
    "reverse-complement"
    {:description "generate the reverse complement for a dna string"
     :normalizer dna/normalize
     :function dna/reverse-complement
     :parser util/single-line
     }
    "gc-content"
    {:description "find the string with the highest GC content"
     :normalizer normalize-fasta-dna
     :function (fn [fasta] (reduce gc-max fasta))
     :parser util/fasta
     :formatter (fn [{:keys [id sequence]}] (println id) (println (dna/gc-content sequence)))
     }
    }})
