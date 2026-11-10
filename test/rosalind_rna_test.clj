(ns rosalind-rna-test
  (:require [clojure.test :refer [deftest is testing]]
            [rosalind-rna :as rna]))

(deftest dna->rna
  (testing "convert a DNA string to an RNA string"
    (is (= "" (rna/dna->rna "")))
    (is (= "ACGU" (rna/dna->rna "ACGT")))
    (is (= "GAUGGAACUUGACUACGUAAAUU" (rna/dna->rna "GATGGAACTTGACTACGTAAATT")))))
