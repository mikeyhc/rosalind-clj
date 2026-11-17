(ns rosalind-cli.protein-commands
  (:require [rosalind-cli.utility :as util]
            [rosalind-protein :as protein]
            [rosalind-rna :as rna]))

(def commands
  {:description "commands related to protein"
   :commands
   {"from-rna" {:description "convert an RNA string to a Protein string"
                :normalizer rna/normalize
                :function protein/rna->protein
                :parser util/single-line}}})
