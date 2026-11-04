package com.smartcity.grpc.service;

import com.smartcity.grpc.model.AlertEntity;
import com.smartcity.grpc.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.smartcity.grpc.AlertRequest;
import com.smartcity.grpc.AlertResponse;
import com.smartcity.grpc.AlertServiceGrpc; // Votre classe g�n�r�e
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.UUID;

// @GrpcService indique � Spring Boot que c'est un service gRPC officiel
@GrpcService
public class AlertServiceImpl extends AlertServiceGrpc.AlertServiceImplBase {

    @Autowired
    private AlertRepository repository; // Injection du repo

    @Override
    public void sendAlert(AlertRequest request, StreamObserver<AlertResponse> responseObserver) {
        // 1. Sauvegarde en Base de Données REELLE
        AlertEntity entity = new AlertEntity(request.getType().toString(), request.getLocation());
        repository.save(entity);
        
        System.out.println("💾 Alerte sauvegardée en DB : " + entity);

        // 2. Réponse gRPC
        String msg = String.format("Alerte %s à %s enregistrée en base à %s.", 
                                   request.getType(), request.getLocation(), entity.getTimestamp());
        
        AlertResponse response = AlertResponse.newBuilder()
                .setMessage(msg)
                .setSeverity(1)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}