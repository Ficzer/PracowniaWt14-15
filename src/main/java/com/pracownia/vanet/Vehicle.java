package com.pracownia.vanet;

import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Data;

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

	public void update(){
		if(iterator >= route.yCoordinates.size()){
			iterator = 0;
		}

		currentX = route.xCoordinates.get(iterator);
		currentY = route.yCoordinates.get(iterator);

		System.out.println(id + " " + currentX + " " + currentY);
		iterator++;
	}
}
