(ns ^:figwheel-hooks hilih-lang.core
  (:require
    [goog.dom :as gdom]
    [clojure.string :as str]
    [reagent.core :as reagent
     :refer           [atom]]))

;; state
(defonce app-state (atom {:text ""}))

(defn convert-to-hilih [text]
  (str/replace text #"[aueo]" "i"))

;; layouts
(defn section []
  (fn []
    (let [text (:text @app-state)]
      [:div.section
       [:h1.section__title (convert-to-hilih "Convert words to Hilih Language")]
       [:textarea
        {:rows          10
         :default-value (:text @app-state)
         :placeholder   "Please input words to be converted to hilih lang..."
         :on-change     #(swap! app-state assoc-in [:text] (-> % .-target .-value))}]
       [:h3
        (if (= text "")
          ""
          (convert-to-hilih "Result :"))
        [:br]
        [:br]
        (convert-to-hilih text)]])))

;; setup
(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (reagent/render-component [section] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element)

(defn ^:after-load on-reload []
  (mount-app-element))
