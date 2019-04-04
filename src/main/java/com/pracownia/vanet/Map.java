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
		routes.add(new Route(400.0, 100.0, 400.0, 700.0));
		routes.add(new Route(600.0, 100.0, 600.0, 700.0));
		routes.add(new Route(800.0, 100.0, 800.0, 700.0));
		routes.add(new Route(100.0, 200.0, 900.0, 200.0));
		routes.add(new Route(100.0, 400.0, 900.0, 400.0));
		routes.add(new Route(100.0, 600.0, 900.0, 600.0));
		crossings.add(new Crossing(new Point(200.0, 200.0), routes.get(0), routes.get(4)));
		crossings.add(new Crossing(new Point(200.0, 400.0), routes.get(0), routes.get(5)));
		crossings.add(new Crossing(new Point(200.0, 600.0), routes.get(0), routes.get(6)));
		crossings.add(new Crossing(new Point(400.0, 200.0), routes.get(1), routes.get(4)));
		crossings.add(new Crossing(new Point(400.0, 400.0), routes.get(1), routes.get(5)));
		crossings.add(new Crossing(new Point(400.0, 600.0), routes.get(1), routes.get(6)));
		crossings.add(new Crossing(new Point(600.0, 200.0), routes.get(2), routes.get(4)));
		crossings.add(new Crossing(new Point(600.0, 400.0), routes.get(2), routes.get(5)));
		crossings.add(new Crossing(new Point(600.0, 600.0), routes.get(2), routes.get(6)));
		crossings.add(new Crossing(new Point(800.0, 200.0), routes.get(3), routes.get(4)));
		crossings.add(new Crossing(new Point(800.0, 400.0), routes.get(3), routes.get(5)));
		crossings.add(new Crossing(new Point(800.0, 600.0), routes.get(3), routes.get(6)));

		vehicles.add(new Vehicle(routes.get(0), 0, 10.0, 0.4));
	}


}
