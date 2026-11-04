package com.smartcity.graphql.model;

public class Route {
    private String start;
    private String destination;
    private Integer duration; // Préférez Integer à int (plus sûr pour GraphQL)
    private String transportMode;

    // ✅ 1. CONSTRUCTEUR VIDE (OBLIGATOIRE)
    public Route() {
    }

    // ✅ 2. CONSTRUCTEUR AVEC PARAMÈTRES (Pour votre Controller)
    public Route(String start, String destination, Integer duration, String transportMode) {
        this.start = start;
        this.destination = destination;
        this.duration = duration;
        this.transportMode = transportMode;
    }

    // ✅ 3. GETTERS (Indispensables pour que GraphQL lise les données)
    public String getStart() { return start; }
    public String getDestination() { return destination; }
    public Integer getDuration() { return duration; }
    public String getTransportMode() { return transportMode; }
    
    // Setters (Optionnels mais recommandés)
    public void setStart(String start) { this.start = start; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public void setTransportMode(String transportMode) { this.transportMode = transportMode; }
}