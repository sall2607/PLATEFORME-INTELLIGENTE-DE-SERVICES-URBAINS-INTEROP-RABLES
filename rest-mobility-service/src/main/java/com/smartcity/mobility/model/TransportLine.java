package com.smartcity.mobility.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TransportLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;        // BUS, METRO, TRAIN
    private String lineCode;    // L4, M2, ...
    private String origin;
    private String destination;
    private String status;      // ON_TIME, DELAYED, CANCELLED
    private String nextArrival; // "14:35"
}
