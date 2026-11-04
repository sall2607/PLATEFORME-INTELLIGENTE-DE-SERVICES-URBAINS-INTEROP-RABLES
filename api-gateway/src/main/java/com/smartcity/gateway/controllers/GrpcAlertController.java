package com.smartcity.gateway.controllers;

import com.smartcity.gateway.clients.GrpcAlertClient;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class GrpcAlertController {
    
    private final GrpcAlertClient client;
    
    public GrpcAlertController(GrpcAlertClient c){
        this.client = c;
    }

    @GetMapping(produces = "application/json") 
    public Map<String, String> send(@RequestParam("type") String type, @RequestParam("location") String location){
        String responseMessage = client.sendAlert(type, location);
        return Collections.singletonMap("result", responseMessage);
    }
}