package com.smartcity.mobility.service;

import com.smartcity.mobility.model.TransportLine;
import com.smartcity.mobility.repository.TransportLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MobilityService {

    private final TransportLineRepository repo;

    public MobilityService(TransportLineRepository repo) {
        this.repo = repo;
    }

    public List<TransportLine> findAll() { return repo.findAll(); }

    public Optional<TransportLine> findById(Long id){ return repo.findById(id); }

    public List<TransportLine> findByLineCode(String code){ return repo.findByLineCode(code); }

    public TransportLine save(TransportLine line){ return repo.save(line); }

    public void delete(Long id){ repo.deleteById(id); }
}
