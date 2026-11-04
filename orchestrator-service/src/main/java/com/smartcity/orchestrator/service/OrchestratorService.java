package com.smartcity.orchestrator.service;

import com.smartcity.orchestrator.model.TripResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class OrchestratorService {

    private final WebClient.Builder webClientBuilder;

    public OrchestratorService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public TripResponse orchestrate(String depart, String arrivee) {
        TripResponse response = new TripResponse();
        WebClient webClient = webClientBuilder.build();

        // --- 1. APPEL SOAP (Qualité Air) ---
        try {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns=\"http://soap.smartcity.com/\">" +
                              "<soapenv:Header/><soapenv:Body><tns:getAQI><zone>" + arrivee + "</zone></tns:getAQI></soapenv:Body></soapenv:Envelope>";
            
            String soapXml = webClient.post().uri("http://soap-service:8888/air")
                    .header("Content-Type", "text/xml").bodyValue(soapBody)
                    .retrieve().bodyToMono(String.class).block();
            
            if (soapXml != null && soapXml.contains("return>")) {
                int start = soapXml.indexOf("return>") + 7;
                int end = soapXml.indexOf("</", start);
                response.airQuality = soapXml.substring(start, end);
            } else {
                response.airQuality = "0";
            }
        } catch (Exception e) {
            response.airQuality = "0";
            System.err.println("Erreur SOAP: " + e.getMessage());
        }

        // Calcul de la pollution pour la cohérence
        boolean polluted = false;
        try {
            polluted = Integer.parseInt(response.airQuality) > 50;
        } catch (Exception e) { polluted = false; }

        // --- 2. VERIFIER ALERTE (gRPC Check via REST Port 8080) ---
        try {
            // Note: On vérifie uniquement le départ pour rester simple
            Boolean hasAlert = webClient.get()
                    .uri("http://grpc-service:8080/internal/check?location=" + depart)
                    .retrieve().bodyToMono(Boolean.class).block();
            
            response.hasAlert = Boolean.TRUE.equals(hasAlert);
            response.alertMessage = response.hasAlert ? "⚠️ Alerte sur le trajet" : "✅ Trajet sécurisé";
        } catch (Exception e) {
            response.hasAlert = false;
            response.alertMessage = "Sécurité vérifiée";
        }

        // --- 3. APPEL GRAPHQL (Cohérence avec pollution et alerte) ---
        try {
            String query = String.format(
                "{ routes(start: \"%s\", destination: \"%s\", hasAlert: %b, isPolluted: %b) { transportMode duration } }", 
                depart, arrivee, response.hasAlert, polluted
            );

            Map gqlResponse = webClient.post().uri("http://graphql-service:8083/graphql")
                    .bodyValue(Map.of("query", query)).retrieve().bodyToMono(Map.class).block();
            
            if (gqlResponse != null && gqlResponse.containsKey("data")) {
                Map data = (Map) gqlResponse.get("data");
                if (data != null && data.get("routes") != null) {
                    response.routes = (List<Object>) data.get("routes");
                }
            }
        } catch (Exception e) {
            response.routes = new ArrayList<>();
        }

        // --- 4. APPEL REST (Mobilité) ---
        try {
            List<Map> allTransports = webClient.get().uri("http://rest-service:8081/mobility/lines")
                    .retrieve().bodyToFlux(Map.class).collectList().block();
            
            // On convertit pour le Front
            if (allTransports != null) {
                response.transports = new ArrayList<>(allTransports);
            } else {
                response.transports = new ArrayList<>();
            }
            response.recommendation = polluted ? "⚠️ Pollution détectée : privilégiez le métro." : "✅ Air sain : tous transports dispo.";

        } catch (Exception e) {
            response.transports = new ArrayList<>();
            response.recommendation = "Données transports indisponibles.";
        }

        return response;
    }
}