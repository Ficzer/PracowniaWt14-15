package com.pracownia.vanet;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Przechoowuje samochody i ich powiązania z trasami.
 */

@Data
public class Map {

	private double width = 1000.0;
	private double height = 900.0;

	List<Route> routes;
	List<Vehicle> vehicles;
	List<Crossing> crossings;


	public Map(){

		routes = new ArrayList<>();
		vehicles = new ArrayList<>();
		crossings = new ArrayList<>();
		initMap();

	}

	private void initMap()
	{
		routes.add(new Route(200.0, 100.0, 200.0, 700.0));
		routes.add(new Route(100.0, 200.0, 900.0, 200.0));

		vehicles.add(new Vehicle(routes.get(0), 0, 10.0, 0.4));
	}


}
