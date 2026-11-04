package com.smartcity.grpc.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class AlertEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type;
    private String location;
    private LocalDateTime timestamp;

    // Constructeur vide (Obligatoire pour JPA)
    public AlertEntity() {}

    // Constructeur avec paramètres
    public AlertEntity(String type, String location) {
        this.type = type;
        this.location = location;
        this.timestamp = LocalDateTime.now();
    }

    // ==========================================
    //  LES GETTERS MANQUANTS SONT ICI 
    // ==========================================

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLocation() { // C'était l'erreur dans InternalCheckController
        return location;
    }

    public LocalDateTime getTimestamp() { // C'était l'erreur dans AlertServiceImpl
        return timestamp;
    }

    // Setters (Optionnels mais utiles)
    public void setType(String type) { this.type = type; }
    public void setLocation(String location) { this.location = location; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}