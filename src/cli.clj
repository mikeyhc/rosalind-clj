(ns cli
  (:require [clojure.string :as s]
            [rosalind-dna :as dna]))

(declare single-line is-dna)

(defmacro as-error
  [& blocks]
  `(binding [*out* *err*]
     ~@blocks))

(def commands
  {"dna"
   {:description "commands related to DNA"
    :commands
    {"count" {:description "count the nucleotides in the provided DNA\n"
              :normalizer dna/normalize
              :function dna/count-nucleotides
              :parser (fn [a] (single-line a))
              :formatter (fn [{a :A c :C g :G t :T}] (println a c g t))
              }}}
   })

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

(defn single-line
  [args]
  (if (= (count args) 0)
    (read-line)
    (with-open [rdr (clojure.java.io/reader (first args))]
      (first (line-seq rdr)))))
