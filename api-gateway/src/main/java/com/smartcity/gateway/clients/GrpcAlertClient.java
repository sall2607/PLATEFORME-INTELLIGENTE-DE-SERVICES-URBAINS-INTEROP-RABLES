package com.smartcity.gateway.clients;
import io.grpc.*;

import com.smartcity.grpc.AlertServiceGrpc;
import com.smartcity.grpc.AlertRequest;
import com.smartcity.grpc.AlertResponse;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
@Component 
public class GrpcAlertClient{
	private final AlertServiceGrpc.AlertServiceBlockingStub stub;
	public GrpcAlertClient(@Value("${grpc.alert.host}")String host,@Value("${grpc.alert.port}")int port){
		ManagedChannel c=ManagedChannelBuilder.forAddress(host,port).usePlaintext().build();
		stub=AlertServiceGrpc.newBlockingStub(c);
		}
	
	public String sendAlert(String type,String location){
		AlertRequest r=AlertRequest.newBuilder().setAlertType(type).setLocation(location).build();
		return stub.sendAlert(r).getMessage();
		}
	
}