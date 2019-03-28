package com.pracownia.vanet;

import com.pracownia.vanet.net.Obu;
import lombok.Getter;

/**
 * Samochód. Oczywiście trzeba tu by dodać zasięg itp.
 */
public class Vehicle {

	int id;
	@Getter private Point coordinates;

	Route route;
	int iterator;
	@Getter	private Obu obu = new Obu();

	//car properties like speed etc
	double speed;

	public Vehicle(Route route, int id) {
		this.route = route;
		iterator = 0;
		this.id = id;
		this.coordinates = new Point(route.xCoordinates.get(0), route.getYCoordinates().get(0));
	}

	public void update() {
		if(iterator >= route.yCoordinates.size()){
			iterator = 0;
		}
		coordinates = new Point(route.xCoordinates.get(iterator), route.yCoordinates.get(iterator));

		System.out.println(id + " " + coordinates.getX() + " " + coordinates.getY());
		iterator++;
	}
}
