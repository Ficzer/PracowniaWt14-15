package com.pracownia.vanet;

import javafx.scene.paint.Color;
import lombok.Getter;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Samochód. Oczywiście trzeba tu by dodać zasięg itp.
 */


@Data
public class Vehicle {

	int id;
	double currentX;
	double currentY;
	double range;
	Route route;
	int iterator;

	//car properties like speed etc
	double speed;

	public Vehicle(Route route, int id, double range){
		this.route = route;
		iterator = 0;
		this.id = id;
		this.range = range;
	}

	public void update(List<Route> routes){
		if(iterator >= route.yCoordinates.size()){
			iterator = 0;
		}

		currentX = route.xCoordinates.get(iterator);
		currentY = route.yCoordinates.get(iterator);

		System.out.println(id + " " + currentX + " " + currentY);
		iterator++;

		Random random = new Random();
		for (Route r : routes) {
			int idxY = r.yCoordinates.indexOf((int) currentY);
			int idxX = r.xCoordinates.indexOf((int) currentX);

			if (r != route && iterator > 0 && random.nextDouble() <= 0.2 &&
					r.xCoordinates.contains((int) currentX) && r.yCoordinates.contains((int) currentY) &&
					r.xCoordinates.get(idxY) == currentX && r.yCoordinates.get(idxY) == currentY) {
				/*route = r;
				break;*/

				if (Objects.equals(route.xCoordinates.get(iterator - 1), route.xCoordinates.get(iterator))) {
					if (idxY + 1 < r.yCoordinates.size() && !Objects.equals(r.yCoordinates.get(idxY), r.yCoordinates.get(idxY + 1))) {
						route = r;
						break;
					}
				}
				else if (Objects.equals(route.yCoordinates.get(iterator - 1), route.yCoordinates.get(iterator))) {
					if (idxY + 1 < r.xCoordinates.size() && !Objects.equals(r.xCoordinates.get(idxY), r.xCoordinates.get(idxY + 1))) {
						route = r;
						break;
					}
				}

				// TODO: add check if there's another vehicle already
			}
		}
	}
}
