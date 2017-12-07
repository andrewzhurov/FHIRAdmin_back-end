(ns back-end.api
  (:require
   fhir-spec.compojure
   [compojure.api.sweet :refer :all]
   [back-end.core :refer [config]]))

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "FHIRAdmin API"
                   :description "API for FHIRAdmin demo manipulations"}
            :tags [{:name "fhir-resources" :description "FHIR resource interactions"}]}}}
   (context "/fhir" []
     :tags ["fhir"]
     :middleware [(partial fhir-spec.compojure/transit-versions-middleware (:fhir-version config))]
     (fhir-spec.compojure/generate-resources-endpoints [{:version fhir-version :type :Patient :ends {:read #(println "Req on Patient Read" %)
                                                                                                     :create #(println "Req on Patient Create")
                                                                                                     :patch #(println "Req on Patient Patch")}}])))) 
