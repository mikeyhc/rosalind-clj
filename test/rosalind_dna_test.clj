(ns rosalind-dna-test
  (:require [clojure.test :refer [deftest is testing]]
            [rosalind-dna :as dna]
            [rosalind-testing :as rt]))

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

(deftest reverse-complement
  (testing "reverse complement produces the correct string"
    (is (= "" (dna/reverse-complement "")))
    (is (= "ACGT" (dna/reverse-complement "ACGT")))
    (is (= "ACCGGGTTTT" (dna/reverse-complement "AAAACCCGGT")))))

(deftest gc-content
  (testing "test that GC content produces correct values"
    (is (rt/within-bounds 0.0 (dna/gc-content "ATAT")))
    (is (rt/within-bounds 100.0 (dna/gc-content "GCGC")))
    (is (rt/within-bounds 50.0 (dna/gc-content "ATGC")))
    (is (rt/within-bounds 60.91954 (dna/gc-content "CCACCCTCGTGGTATGGCTAGGCATTCAGGAACCGGAGAACGCTTCAGACCAGCCCGGACTGGGAACCTGCGGGCAGTAGGTGGAAT")))))

(deftest point-mutations
  (testing "test that point mutations are correct"
    (is (= 0 (dna/point-mutations "" "")))
    (is (= 1 (dna/point-mutations "A" "T")))
    (is (= 2 (dna/point-mutations "AT" "TA")))
    (is (= 7 (dna/point-mutations "GAGCCTACTAACGGGAT" "CATCGTAATGACGGCCT")))))
