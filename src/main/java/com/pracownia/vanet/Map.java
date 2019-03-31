package com.pracownia.vanet;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Przechoowuje samochody i ich powiązania z trasami.
 */

@Data
public class Map {

	private double width = 450.0;
	private double height = 270.0;

	List<Route> routes;
	ArrayList<Vehicle> vehicles;

	public Map(){
		ReadRoutes readRoutes = new ReadRoutes("./routes/");
		routes = readRoutes.readAllRoutes();
		vehicles = new ArrayList<>();

		for(int i = 0; i<routes.size(); i++){
			vehicles.add(new Vehicle(routes.get(i), i, 60));
		}


	}


}
