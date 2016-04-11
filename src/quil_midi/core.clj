(ns quil-midi.core
  (:require
   [clojure.core.match :refer [match]]
   [quil.core :as q]
   [quil.middleware :as m]
   [quil-midi.midi :as midi])
  (:import [processing.video Capture]))

(declare midi-to-255 log-msg update-atom)

(def state (atom {:val 0}))

;; DISPATCH
(defn dispatch-midi-event
  [midi-msg]
  (let [{n :note v :velocity :as msg} midi-msg]
    (match n
        0 (update-atom v))))


(defn update-atom [val]
    (if (not= val (:val @state))
      (swap! state assoc :val val)
      nil))

(midi/listener dispatch-midi-event)

(defn setup []

  (Capture/list)
  ;;(q/background 0)
  (q/frame-rate 10))

(defn draw []
  ;;(q/background (midi-to-255 (:val @state)))
  ;;(q/stroke 255 0 0)
  ;;(q/stroke-weight 5)
  ;;(q/line 0 0  (/ (* (:val @state) 400) 255) (/ (* (:val @state) 400) 255))

  )

(q/defsketch quil-midi
  :size [400, 400]
  :setup setup
  :renderer :p3d
  :draw draw)

;; util

(defn midi-to-255 [x]
  (/ (* x 255) 127))

;; main

(defn -main [] (print "loaded"))
