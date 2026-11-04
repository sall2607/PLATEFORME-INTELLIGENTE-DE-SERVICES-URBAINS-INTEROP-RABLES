package com.smartcity.grpc.controller;

import com.smartcity.grpc.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class InternalCheckController {

    @Autowired
    private AlertRepository repository; // Je l'ai nomme 'repository' pour etre sur

    @GetMapping("/check")
    public boolean checkAlert(@RequestParam("location") String location) {
        // Verification simple en base de donnees
        return repository.findAll().stream()
                .anyMatch(a -> a.getLocation().equalsIgnoreCase(location));
    }
}