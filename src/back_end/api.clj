(ns back-end.api
  (:require [compojure.api.sweet :refer [defapi api context GET PUT PATCH DELETE POST resource]]
            [ring.util.http-response :refer [ok]]
            [org.httpkit.server :as server]
            [schema.core :as s]

            fhir-spec.compojure
            [back-end.core :refer [config]]))

(declare app)
(defonce *server (atom nil))

(defn start-server []
  (reset! *server (server/run-server #'app {:port 3001})))

(defn stop-server []
  (when-not (nil? @*server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@*server :timeout 100)
    (reset! *server nil)))
(defn restart-server [] (stop-server) (start-server))


(defn dull-handler [req]
  (println "Req:" req)
  (ok {:body-params-in-handler (:body-params req)}))


(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"}}
   (GET "/hello" []
     :query-params [name :- String]
     :middleware nil
     (ok {:message (str "Hello, " name)}))
   (fhir-spec.compojure/fhir-resources
    {:desired-version "3.0.1"}
    {"3.0.1" {:Patient {:read dull-handler
                        :vread dull-handler
                        :update #()
                        :patch #()
                        :delete #()
                        :create dull-handler
                        }
              :Coverage {:read #()}}
     "1.8.0" {:Patient {:create dull-handler}}})
   #_(fhir-spec.compojure/fhir-resources-flat
    [{:desired-version "3.0.1" :version "3.0.1" :type :Patient :ends {:read dull-handler}}
     {:desired-version "3.0.1" :version "1.8.0" :type :Patient :ends {:read dull-handler}}
     {:desired-version "3.0.1" :version "3.0.1" :type :Coverage :ends {:read dull-handler}}
     {:desired-version "3.0.1" :version "1.0.2" :type :Coverage :ends {:read dull-handler}}])))
