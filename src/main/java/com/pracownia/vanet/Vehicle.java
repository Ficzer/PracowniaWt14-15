package com.pracownia.vanet;

import com.pracownia.vanet.net.Obu;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
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
			int idxY = r.yCoordinates.indexOf((int) coordinates.getY());
			int idxX = r.xCoordinates.indexOf((int) coordinates.getX());

			if (r != route && iterator > 0 && random.nextDouble() <= 0.1 &&
					r.xCoordinates.contains((int) coordinates.getX()) && r.yCoordinates.contains((int) coordinates.getY()) &&
					r.xCoordinates.get(idxX) == coordinates.getX() && r.yCoordinates.get(idxY) == coordinates.getY()) {
				/*route = r;
				break;*/

				// not quite sure about this part
				if (Objects.equals(route.xCoordinates.get(iterator - 1), route.xCoordinates.get(iterator))) {
					if (idxY + 1 < r.yCoordinates.size() && !Objects.equals(r.yCoordinates.get(idxY), r.yCoordinates.get(idxY + 1))) {
						route = r;
						break;
					}
				}
				else if (Objects.equals(route.yCoordinates.get(iterator - 1), route.yCoordinates.get(iterator))) {
					if (idxX + 1 < r.xCoordinates.size() && !Objects.equals(r.xCoordinates.get(idxX), r.xCoordinates.get(idxX + 1))) {
						route = r;
						break;
					}
				}
			}
		}
	}
}
