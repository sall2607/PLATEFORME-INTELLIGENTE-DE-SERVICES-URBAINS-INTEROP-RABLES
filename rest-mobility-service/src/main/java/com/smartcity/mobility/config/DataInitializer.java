package com.smartcity.mobility.config;

import com.smartcity.mobility.model.TransportLine;
import com.smartcity.mobility.repository.TransportLineRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final TransportLineRepository repo;

    public DataInitializer(TransportLineRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        repo.save(TransportLine.builder().type("BUS").lineCode("B12").origin("Station A").destination("Station B").status("ON_TIME").nextArrival("12:05").build());
        repo.save(TransportLine.builder().type("METRO").lineCode("M2").origin("Center").destination("North").status("DELAYED").nextArrival("12:12").build());
        repo.save(TransportLine.builder().type("TRAIN").lineCode("TGM").origin("Tunis").destination("La Marsa").status("ON_TIME").nextArrival("12:30").build());
        repo.save(TransportLine.builder().type("BUS").lineCode("L4").origin("City").destination("Harbor").status("CANCELLED").nextArrival("--").build());
    }
}
