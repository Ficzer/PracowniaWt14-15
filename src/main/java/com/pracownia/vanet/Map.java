package com.pracownia.vanet;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Przechoowuje samochody i ich powiązania z trasami.
 * Trasy możnaby odczytywać  jakiegoś pliku żeby było wygodniej.
 */

@Data
public class Map {

	private static double width = 1000.0;
	private static double height = 1000.0;

	List<Route> routes; // keeps all posible routes;
	List<Vehicle> vehicles;

	public Map(){
		routes = new ArrayList<>();
		vehicles = new ArrayList<>();

		Route route = new Route();
		Route route2 = new Route();
		Route route3 = new Route();

		route.route1();
		route2.route2();
		route3.route3();

		routes.add(route);
		routes.add(route2);
		routes.add(route3);

		vehicles.add(new Vehicle(route, 0));
		vehicles.add(new Vehicle(route2, 1));
		vehicles.add(new Vehicle(route3, 2));
	}


}
