package com.smartcity.graphql.service;

import com.smartcity.graphql.model.Route;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RouteService {
    public List<Route> findRoutes(String start, String destination) {
        return List.of(
            new Route(start, destination, 12, "Bus"),
            new Route(start, destination, 8, "Metro"),
            new Route(start, destination, 15, "Tramway")
        );
    }
}
