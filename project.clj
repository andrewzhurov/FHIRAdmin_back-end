(defproject back-end "0.1.0"
  :description "FHIRAdmin back-end"
  :dependencies [[org.clojure/clojure "1.9.0-RC2"]
                 [fhir-spec "0.1.0"]
                 [http-kit "2.2.0"]
                 [metosin/compojure-api "2.0.0-alpha16"]
                 [metosin/spec-tools "0.5.1"]
                 [manifold "0.1.6"]
                 
                 [org.postgresql/postgresql "42.1.4"]
                 [korma "0.4.3"]]

  :ring {:init back-end.core/init
         :handler back-end.api/app
         :async? true
         :port 3001}
  :profiles {:dev {:plugins [[lein-ring "0.12.2"]]}})
