(ns rosalind-cli.num-commands
  (:require [rosalind-cli.utility :as util]
            [rosalind-num :as rnum]))

(defn map-to-int
  [l]
  (map Integer/parseInt l))

(def commands
  {:description "general number functions"
   :commands
   {"fib-rabbits" {:description "calculate the rabbits from the fib problem"
                   :normalizer (fn [l] (map-to-int l))
                   :function (fn [args] (apply rnum/fib-rabbits args))
                   :parser util/single-line-parts}
    "mendels" {:description "calculate the chance of a dominant alelle in a population"
               :normalizer (fn [l] (map-to-int l))
               :function (fn [args] (float (apply rnum/mendels args)))
               :parser util/single-line-parts}}})
