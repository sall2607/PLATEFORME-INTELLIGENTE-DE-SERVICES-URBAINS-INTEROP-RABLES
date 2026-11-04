package com.smartcity.mobility.repository;

import com.smartcity.mobility.model.TransportLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportLineRepository extends JpaRepository<TransportLine, Long> {
    List<TransportLine> findByLineCode(String lineCode);
}
