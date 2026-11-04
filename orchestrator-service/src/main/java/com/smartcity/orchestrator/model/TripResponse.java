package com.smartcity.orchestrator.model;
import java.util.List;

public class TripResponse {
    public String airQuality;
    public String alertMessage;
    public boolean hasAlert;
    public List<Object> routes; // Résultat GraphQL
    public List<Object> transports; // Résultat REST
    public String recommendation;
}