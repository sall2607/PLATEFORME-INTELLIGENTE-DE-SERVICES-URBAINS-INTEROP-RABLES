package com.smartcity.graphql.controller;

import com.smartcity.graphql.model.Route;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteGraphQLController {

	@QueryMapping
	public List<Route> routes(@Argument("start") String start, 
	                          @Argument("destination") String destination, 
	                          @Argument("hasAlert") Boolean hasAlert,
	                          @Argument("isPolluted") Boolean isPolluted) { // Nouveau
	    
	    List<Route> routes = new ArrayList<>();

	    // LOGIQUE DE FILTRAGE COHÉRENTE
	    if (Boolean.TRUE.equals(hasAlert)) {
	        routes.add(new Route(start, destination, 60, "Metro (Securise - Accident route)"));
	    } 
	    else if (Boolean.TRUE.equals(isPolluted)) {
	        // SI POLLUTION : On ne propose que des modes propres
	        routes.add(new Route(start, destination, 45, "Tramway (Eco)"));
	        routes.add(new Route(start, destination, 55, "Velo / Marche"));
	    } 
	    else {
	        // CAS NORMAL : Tout est disponible
	        routes.add(new Route(start, destination, 20, "Taxi Rapide"));
	        routes.add(new Route(start, destination, 45, "Bus Ligne C"));
	    }
	    return routes;
	}
}