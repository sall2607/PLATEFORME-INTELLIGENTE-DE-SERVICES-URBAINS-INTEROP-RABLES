package com.smartcity.orchestrator.controller;

import com.smartcity.orchestrator.model.TripResponse;
import com.smartcity.orchestrator.service.OrchestratorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orchestrator")
public class OrchestratorController {

    private final OrchestratorService service;

    public OrchestratorController(OrchestratorService service) {
        this.service = service;
    }

    @GetMapping("/plan")
    public TripResponse planTrip(@RequestParam("depart") String depart, 
                                 @RequestParam("arrivee") String arrivee) {
        return service.orchestrate(depart, arrivee);
    }
}