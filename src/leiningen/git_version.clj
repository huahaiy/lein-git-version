(ns leiningen.git-version
  (:require [leiningen.help]
            [leiningen.jar]
            [leiningen.compile]
            [leiningen.core.main]
            [leiningen.core.project]
            [robert.hooke]
            [leiningen.test])
  (:use
   [clojure.java.shell :only [sh]]))

(defn get-git-version
  []
  (apply str (rest (clojure.string/trim
                    (:out (sh
                           "git" "describe" "--match" "v*.*"
                           "--abbrev=4" "--dirty=**DIRTY**"))))))

(defn get-git-rev
  []
  (clojure.string/trim
                    (:out (sh
                           "git" "rev-parse" "--short" "HEAD"))))

(defn get-git-last-tag
  []
  (clojure.string/trim (:out (apply sh (clojure.string/split "git describe --tags --abbrev=0" #" ")))))

(defn get-git-commits-since-last-tag
  []
  (let [last-tag (get-git-last-tag)]
    (count
      (:out (sh
              "git" "log" (str last-tag "..HEAD") "--oneline")))))
                         
(defn numcommits-gitrev
  []
  (str (get-git-last-tag) "+" (get-git-commits-since-last-tag) "-" (get-git-rev)))


(defn git-version
  "Show project version, as tagged in git."
  ^{:doc "Show git project version"}
  [project & args]
  (println (numcommits-gitrev)))
