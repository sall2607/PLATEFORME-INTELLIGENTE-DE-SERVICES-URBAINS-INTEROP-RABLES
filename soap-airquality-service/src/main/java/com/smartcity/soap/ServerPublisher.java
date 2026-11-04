package com.smartcity.soap;

import jakarta.xml.ws.Endpoint;

public class ServerPublisher {
    public static void main(String[] args) {
        String url = "http://0.0.0.0:8888/air";
        Endpoint.publish(url, new AirQualityServiceImpl());
        System.out.println("Server published at " + url);
    }
}
