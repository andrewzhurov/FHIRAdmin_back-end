(ns back-end.core
  (:require
   [korma.db :as db]))

(def config
  {:jdbc-postgres {}
   :fhir-version "3.0.1"})

(defn start-db [config]
  (db/default-connection (db/create-db (db/postgres (get config :jdbc-postgres)))))

(defn init []
  #_(start-db config))


