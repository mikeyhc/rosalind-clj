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

(defn mendels
  "Determine the probabilty of 2 randomly selected mating organism will produce
  an offspring possessing a dominant allele, takes k (total homozygous dominant),
  m (hetrozygous), and n (homozygous recessive)"
  [k m n]
  (let [t (+ k m n)]
    (+ (/ k t) ; k * any
       (/ m t 2) ; m(dom) * any
       (* (/ m t 2) (/ k (dec t))) ; m(rec) * k
       (* (/ m t 2) (/ (dec m) (dec t) 2)) ; m(rec) * m(dom)
       (* (/ n t) (/ k (dec t))) ; n * k
       (* (/ n t) (/ m (dec t) 2))))) ; n * m(dom)
