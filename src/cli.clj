(ns cli
  (:require [clojure.string :as s]
            [rosalind-dna :as dna]
            [rosalind-cli.dna-commands :as dna-cmds]
            [rosalind-cli.utility :as util]
            [rosalind-rna :as rna]
            [rosalind-num :as rnum]))

(declare map-to-int)

(defmacro as-error
  [& blocks]
  `(binding [*out* *err*]
     ~@blocks))

(def commands
  {"dna" dna-cmds/commands
   "num"
   {:description "general number functions"
    :commands
    {"fib-rabbits" {:description "calculate the rabbits from the fib problem"
                    :normalizer (fn [l] (map-to-int l))
                    :function (fn [args] (apply rnum/fib-rabbits args))
                    :parser util/single-line-parts}}}
   "rna"
   {:description "commands related to RNA"
    :commands
    {"from-dna" {:description "convert a DNA string to an RNA string"
                 :normalizer dna/normalize
                 :function rna/dna->rna
                 :parser util/single-line
                 }}}})

(def usage-header
  "usage: rosalind {group} {command} ARGS\n")

(def group-usage
  (->> commands
       (map (fn [[name {desc :description}]] (str "  " name ": " desc)))
       (s/join "\n")
       (str "groups:\n")))
       
(def command-usage
  (->> commands
       (reduce (fn [acc [name {cmds :commands}]]
                 (reduce (fn [acc0 [n {d :description}]]
                           (cons (str "  " n ": " d) acc0))
                         (cons (str name " commands:") acc)
                         cmds))
               '())
       (reverse)
       (s/join "\n")))

(defn usage
  "Helpful usage information!"
  []
  (println (str usage-header
               "\n"
               group-usage
               "\n\n"
               command-usage
               "\n")))

(defn -main [& args]
  (if (or (and (= (count args) 1) (= (first args) "help"))
          (< (count args) 2))
    (do
      (as-error
       (println "error: group and command required")
       (usage))
      (System/exit (if (= (count args) 1) 0 1))))
  (if-let [group (get commands (s/lower-case (first args)))]
    (if-let [command (get (get group :commands) (s/lower-case (second args)))]
      (let [{parser :parser function :function} command
            normalizer (get command :normalizer (fn [v] v))
            formatter (get command :formatter println)
            maybe-input (parser (drop 2 args))]
        (if-let [input (normalizer maybe-input)]
          (formatter (function input))
          (as-error
           (println (str "invalid input: \"" maybe-input "\"")))))
      (as-error
       (println (str "unknown command: \"" (second args) "\""))
       (usage)))
    (as-error
      (println (str "unknown group: \"" (first args) "\""))
      (usage))))

;; helpers

(defn map-to-int
  [l]
  (map Integer/parseInt l))
