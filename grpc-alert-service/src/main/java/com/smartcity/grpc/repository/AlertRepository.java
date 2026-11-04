package com.smartcity.grpc.repository;
import com.smartcity.grpc.model.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<AlertEntity, Long> {
}