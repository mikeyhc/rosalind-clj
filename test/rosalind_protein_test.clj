(ns rosalind-protein-test
  (:require [clojure.test :refer [deftest is testing]]
            [rosalind-protein :as protein]))

(deftest rna->protein
  (testing "convert RNA into protein"
    (is (= "M" (protein/rna->protein "AUGUAG")))
    (is (= "MA" (protein/rna->protein "AUGGCCUAG")))
    (is (= "MAMAPRTEINSTRING" (protein/rna->protein "AUGGCCAUGGCGCCCAGAACUGAGAUCAAUAGUACCCGUAUUAACGGGUGA")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"no start codon"
                          (protein/rna->protein "GCCUAG")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"not a valid protein string"
                          (protein/rna->protein "AUGGCUAG")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"no stop codon"
                          (protein/rna->protein "AUGGGC")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"invalid rna"
                          (protein/rna->protein "AUGAAXUAG")))))
