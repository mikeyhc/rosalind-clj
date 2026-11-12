(ns rosalind-fasta-test
  (:require [clojure.test :refer [deftest is testing]]
            [rosalind-fasta :as fasta]))

(deftest parse-fasta-lines
  (testing "test that we can parse various FASTA string sequences"
    (is (= [] (fasta/parse-fasta-lines '())))
    (is (= [{:id "seq1" :description "description" :sequence "ATGC"}]
           (fasta/parse-fasta-lines '(">seq1 description" "ATGC"))))
    (is (= [{:id "seq1" :description "" :sequence "ATGC"}]
           (fasta/parse-fasta-lines '(">seq1" "ATGC"))))
    (is (= [{:id "seq1" :description "" :sequence "ATGC"}
            {:id "seq2" :description "description" :sequence "CGTA"}]
           (fasta/parse-fasta-lines '(">seq1" "ATGC" ">seq2 description" "CGTA"))))
    (is (= [{:id "seq1" :description "" :sequence "ATGCCGTA"}]
           (fasta/parse-fasta-lines '(">seq1" "ATGC " " CGTA"))))
    (is (= [{:id "seq1" :description "" :sequence "ATGCCGTA"}
            {:id "seq2" :description "" :sequence "CGTAATGC"}]
           (fasta/parse-fasta-lines '(">seq1" "ATGC" "CGTA" ">seq2" "CGTA" "ATGC"))))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"missing FASTA header"
                          (fasta/parse-fasta-lines '("ATGC"))))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"missing FASTA sequence"
                          (fasta/parse-fasta-lines '(">seq1"))))))

(deftest parse-fasta-string
  (testing "test that we can parse various FASTA strings"
    (is (= [] (fasta/parse-fasta-string "")))
    (is (= [{:id "seq1" :description "description" :sequence "ATGC"}]
           (fasta/parse-fasta-string ">seq1 description\nATGC")))
    (is (= [{:id "seq1" :description "" :sequence "ATGC"}]
           (fasta/parse-fasta-string ">seq1\nATGC")))
    (is (= [{:id "seq1" :description "" :sequence "ATGC"}
            {:id "seq2" :description "description" :sequence "CGTA"}]
           (fasta/parse-fasta-string ">seq1\nATGC\n>seq2 description\nCGTA")))
    (is (= [{:id "seq1" :description "" :sequence "ATGCCGTA"}]
           (fasta/parse-fasta-string ">seq1\nATGC \n CGTA")))
    (is (= [{:id "seq1" :description "" :sequence "ATGCCGTA"}
            {:id "seq2" :description "" :sequence "CGTAATGC"}]
           (fasta/parse-fasta-string ">seq1\nATGC\nCGTA\n>seq2\nCGTA\nATGC\n")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"missing FASTA header"
                          (fasta/parse-fasta-string "ATGC")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"missing FASTA sequence"
                          (fasta/parse-fasta-string ">seq1")))))
