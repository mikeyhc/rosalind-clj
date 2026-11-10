(ns rosalind-num-test
  (:require [clojure.test :refer [deftest is testing]]
            [rosalind-num :as n]))

(deftest fib-rabbits
  (testing "test that the fib rabbits generate correct values"
    (is (= 1 (n/fib-rabbits 1 1)))
    (is (= 1 (n/fib-rabbits 2 1)))
    (is (= 3 (n/fib-rabbits 3 2)))
    (is (= 19 (n/fib-rabbits 5 3)))))
