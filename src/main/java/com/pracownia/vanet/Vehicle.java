package com.pracownia.vanet;

import com.pracownia.vanet.net.Obu;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.List;
import java.util.Random;

/**
 * Samochód. Oczywiście trzeba tu by dodać zasięg itp.
 */
public class Vehicle {

	int id;
	Color color;
	@Getter private Point coordinates;

	Route route;
	int iterator;
	@Getter	private Obu obu = new Obu();

	//car properties like speed etc
	double speed;

	public Vehicle(Route route, int id, Color color) {
		this.route = route;
		iterator = 0;
		this.id = id;
		this.color = color;
		this.coordinates = new Point(route.xCoordinates.get(0), route.getYCoordinates().get(0));
	}

	public void update(List<Route> routes) {
		if(iterator >= route.yCoordinates.size()){
			iterator = 0;
		}
		coordinates = new Point(route.xCoordinates.get(iterator), route.yCoordinates.get(iterator));

		System.out.println(id + " " + coordinates.getX() + " " + coordinates.getY());
		iterator++;

		Random random = new Random();
		for (Route r : routes) {
			if (r != route && random.nextDouble() <= 0.1 &&
					r.xCoordinates.contains((int) coordinates.getX()) && r.yCoordinates.contains((int) coordinates.getY())) {
				route = r;
				break;
			}
		}
	}
}
