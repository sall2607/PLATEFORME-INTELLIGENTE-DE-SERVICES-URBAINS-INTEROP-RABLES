package com.smartcity.mobility.controller;

import com.smartcity.mobility.model.TransportLine;
import com.smartcity.mobility.service.MobilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mobility")
public class TransportLineController {

    private final MobilityService service;

    public TransportLineController(MobilityService service) {
        this.service = service;
    }

    @GetMapping("/lines")
    public List<TransportLine> getAll() {
        return service.findAll();
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<TransportLine> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lines/code/{lineCode}")
    public List<TransportLine> getByCode(@PathVariable String lineCode){
        return service.findByLineCode(lineCode);
    }

    @PostMapping("/lines")
    public TransportLine create(@RequestBody TransportLine line) {
        return service.save(line);
    }

    @PutMapping("/lines/{id}")
    public ResponseEntity<TransportLine> update(@PathVariable Long id, @RequestBody TransportLine updated) {
        return service.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return ResponseEntity.ok(service.save(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.findById(id).map(existing -> {
            service.delete(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // schedule endpoint (simple: returns nextArrival for a line)
    @GetMapping("/schedules/{lineCode}")
    public List<String> schedules(@PathVariable String lineCode){
        return service.findByLineCode(lineCode).stream().map(TransportLine::getNextArrival).toList();
    }

    // traffic summary
    @GetMapping("/traffic")
    public List<String> traffic(){
        return service.findAll().stream()
                .map(l -> l.getLineCode() + " : " + l.getStatus())
                .toList();
    }
}
