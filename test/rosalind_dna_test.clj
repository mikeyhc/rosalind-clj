(ns rosalind-dna-test
  (:require [clojure.test :refer [deftest is testing]]
            [rosalind-dna :as dna]))

(deftest normalize-dna
  (testing "normalizes a DNA string correctly"
    (is (= "CGTA" (dna/normalize "cgta")))
    (is (= "CGTA" (dna/normalize "cGtA")))
    (is (= "CGTA" (dna/normalize "CGTA")))
    (is (= nil (dna/normalize "not dna")))))

(deftest count-nucleotides
  (testing "confirm strings count correctly"
    (is (= {:A 0 :C 0 :G 0 :T 0} (dna/count-nucleotides "")))
    (is (= {:A 1 :C 1 :G 1 :T 1} (dna/count-nucleotides "ACGT")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"not a DNA string"
                          (dna/count-nucleotides "ACGTX")))
    (is (= {:A 20 :C 12 :G 17 :T 21}
           (dna/count-nucleotides "AGCTTTTCATTCTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATAGCAGC")))))
