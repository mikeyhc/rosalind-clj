(ns rosalind-testing)

(def error-bounds 0.001)

(defn within-bounds
  [target value]
  (and (> value (- target error-bounds))
       (< value (+ target error-bounds))))
