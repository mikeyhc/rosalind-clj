(ns cli
  (:require [clojure.string :as s]
            [rosalind-dna :as dna]
            [rosalind-cli.dna-commands :as dna-cmds]
            [rosalind-cli.utility :as util]
            [rosalind-rna :as rna]
            [rosalind-num :as rnum]))

(declare map-to-int stringify)

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
       (map (fn [[name {cmds :commands}]]
              (s/join "\n" (cons (str name" commands:")
                                 (sort
                                  (map (fn [[n {d :description}]] (str "  " n ": " d))
                                       cmds))))))
       (s/join "\n\n")))

(defn usage
  "Helpful usage information!"
  []
  (println (str usage-header
               "\n"
               group-usage
               "\n\n"
               command-usage
               "\n")))

(defn exit-usage
  [& message]
  (if (= (count message) 0)
    (do
      (usage)
      (System/exit 0))
    (do
      (as-error
       (println (s/join " " (cons "error:" message)))
       (usage)
       (System/exit 1)))))

(defn -main
  [& args]
  (cond
    (< (count args) 1) (exit-usage "group required")
    (= (first args) "help") (exit-usage)
    (< (count args) 2) (exit-usage "command required"))
  (if-let [group (get commands (s/lower-case (first args)))]
    (if-let [command (get (get group :commands) (s/lower-case (second args)))]
      (let [{parser :parser function :function} command
            normalizer (get command :normalizer (fn [v] v))
            formatter (get command :formatter println)
            maybe-input (parser (drop 2 args))]
        (if-let [input (normalizer maybe-input)]
          (formatter (function input))
          (as-error
           (println (str "invalid input: \"" (stringify maybe-input) "\""))
           (System/exit 1))))
      (exit-usage (str"unknown command: \"" (second args) "\"")))
    (exit-usage (str "unknown group: \"" (first args) "\""))))

;; helpers
(defn map-to-int
  [l]
  (map Integer/parseInt l))

(defn stringify
  [args]
  (if (seq? args)
    (clojure.string/join " " args)
    (str args)))
