(ns rosalind-num-test
  (:require [clojure.test :refer [deftest is testing]]
            [rosalind-num :as n]
            [rosalind-testing :as rt]))

(deftest fib-rabbits
  (testing "test that the fib rabbits generate correct values"
    (is (= 1 (n/fib-rabbits 1 1)))
    (is (= 1 (n/fib-rabbits 2 1)))
    (is (= 3 (n/fib-rabbits 3 2)))
    (is (= 19 (n/fib-rabbits 5 3)))))

(deftest mendels
  (testing "test that mendels generates correct values"
    (is (rt/within-bounds 0.0 (n/mendels 0 0 2)))
    (is (rt/within-bounds 0.75 (n/mendels 0 2 0)))
    (is (rt/within-bounds 1.0 (n/mendels 2 0 0)))
    (is (rt/within-bounds 0.83333 (n/mendels 1 1 1)))
    (is (rt/within-bounds 0.78333 (n/mendels 2 2 2)))))
