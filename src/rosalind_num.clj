(ns rosalind-num)

(defn fib-rabbits-seq
  "Returns an lazy seq of all the rabbit numbers"
  ([k] (fib-rabbits-seq 0 1 k))
  ([i j k]
   (cons (+ i j) (lazy-seq (fib-rabbits-seq (+ i j) (* i k) k)))))

(defn fib-rabbits
  "Return the n'th generation of rabbits as defined in rosalind fib"
  [n k]
  (last (take n (fib-rabbits-seq k))))
