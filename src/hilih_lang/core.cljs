(ns ^:figwheel-hooks hilih-lang.core
  (:require
    [goog.dom :as gdom]
    [clojure.string :as str]
    [reagent.core :as reagent
     :refer           [atom]]))

;; state
(defonce app-state (atom {:text ""}))

(defn convert-to-hilih [text]
  (str/replace text #"[aiueo]" "i"))

;; layouts
(defn hello-world []
  [:div
   [:h2 "Cinvirt wirds ti hilih lingiigi"]
   [:textarea
    {:rows 10
      :placeholder "Please input words to be converted to hilih lang..."
     :on-change   #(swap! app-state assoc-in [:text] (-> % .-target .-value))}]
   [:h3 (convert-to-hilih (:text @app-state))]])

;; setup
(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (reagent/render-component [hello-world] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element)

(defn ^:after-load on-reload []
  (mount-app-element))
