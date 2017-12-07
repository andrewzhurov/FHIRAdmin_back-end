(ns back-end.core)

(def config
  {:jdbc-postgres {}
   :fhir-version "3.0.1"})

(defn start-db [config]
  (db/default-connection (db/create-db (db/postgres (get config :jdbc-postgres)))))

(defn init []
  (start-db config))


