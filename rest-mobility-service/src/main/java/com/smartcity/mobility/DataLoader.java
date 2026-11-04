package com.smartcity.mobility; // Ou com.smartcity.mobility.config selon où vous l'avez mis

import com.smartcity.mobility.model.TransportLine;
import com.smartcity.mobility.repository.TransportLineRepository; // CORRECTION ICI
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    // CORRECTION ICI : TransportLineRepository au lieu de TransportRepository
    private final TransportLineRepository repository;

    // CORRECTION ICI AUSSI
    public DataLoader(TransportLineRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(TransportLine.builder().type("BUS").lineCode("12B").origin("Gare").destination("Aeroport").status("ON_TIME").nextArrival("14:30").build());
            repository.save(TransportLine.builder().type("METRO").lineCode("M4").origin("Tunis").destination("Bardo").status("DELAYED").nextArrival("14:35").build());
            repository.save(TransportLine.builder().type("TRAM").lineCode("T1").origin("Centre").destination("Plage").status("ON_TIME").nextArrival("14:40").build());
            repository.save(TransportLine.builder().type("BUS").lineCode("20A").origin("Ariana").destination("Sokra").status("CANCELLED").nextArrival("--:--").build());
            
            System.out.println("✅ Base de données PostgreSQL initialisée avec des lignes de transport !");
        }
    }
}