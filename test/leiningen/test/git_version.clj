(ns leiningen.test.git-version
  (:use [leiningen.core.project :only (read)])
  (:use leiningen.git-version)
  (:use [clojure.test]
        [midje.sweet]))

;;(facts
;;  (re-find #"1.0.0" (get-git-version)) => "1.0.0")

(def project (read))

(deftest test-get-git-commits-since-last-tag
  (is (>= (Integer/valueOf (get-git-commits-since-last-tag)) 0))) 

(deftest test-get-git-last-tag
  (is (>= (count (get-git-last-tag)) 0))) 

(deftest test-numcommits-gitrev
  (is (re-matches #"v\d+.\d+.\d+\+\d+\-[\w\d]+" (numcommits-gitrev)))) 
