(ns rosalind-protein)

(def codon-table
  {"UUU" "F"   "CUU" "L" "AUU" "I" "GUU" "V"
   "UUC" "F"   "CUC" "L" "AUC" "I" "GUC" "V"
   "UUA" "L"   "CUA" "L" "AUA" "I" "GUA" "V"
   "UUG" "L"   "CUG" "L" "AUG" "M" "GUG" "V"
   "UCU" "S"   "CCU" "P" "ACU" "T" "GCU" "A"
   "UCC" "S"   "CCC" "P" "ACC" "T" "GCC" "A"
   "UCA" "S"   "CCA" "P" "ACA" "T" "GCA" "A"
   "UCG" "S"   "CCG" "P" "ACG" "T" "GCG" "A"
   "UAU" "Y"   "CAU" "H" "AAU" "N" "GAU" "D"
   "UAC" "Y"   "CAC" "H" "AAC" "N" "GAC" "D"
   "UAA" :stop "CAA" "Q" "AAA" "K" "GAA" "E"
   "UAG" :stop "CAG" "Q" "AAG" "K" "GAG" "E"
   "UGU" "C"   "CGU" "R" "AGU" "S" "GGU" "G"
   "UGC" "C"   "CGC" "R" "AGC" "S" "GGC" "G"
   "UGA" :stop "CGA" "R" "AGA" "R" "GGA" "G"
   "UGG" "W"   "CGG" "R" "AGG" "R" "GGG" "G"})

(defn rna->protein
  "Convert an RNA string to a protein, throws an exception if missing a start
  or stop codon, if the RNA string isn't a multiple of 3, or if a non-RNA character
  is encountered"
  [rna]
  (if-not (= 0 (rem (count rna) 3))
    (throw (ex-info "not a valid protein string" {:input rna}))
    (let [codons (map (partial apply str) (partition 3 rna))]
      (if-not (= (first codons) "AUG")
        (throw (ex-info "no start codon" {:codons codons}))
        (let [aminos (map (partial get codon-table) codons)]
          (cond
            (not (= (last aminos) :stop)) (throw (ex-info "no stop codon" {:codons codons}))
            (some nil? aminos) (throw (ex-info "invalid rna" {:codons codons :rna rna}))
            :else (apply str (drop-last aminos))))))))
