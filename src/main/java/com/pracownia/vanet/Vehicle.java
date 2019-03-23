package com.pracownia.vanet;

import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Samochód. Oczywiście trzeba tu by dodać zasięg itp.
 */
public class Vehicle {

	int id;
	double currentX;
	double currentY;
	Route route;
	int iterator;

	//car properties like speed etc
	double speed;

	public Vehicle(Route route, int id){
		this.route = route;
		iterator = 0;
		this.id = id;
	}

	public void update(){
		if(iterator >= route.yCoordinates.size()){
			iterator = 0;
		}

		currentX = route.xCoordinates.get(iterator);
		currentY = route.yCoordinates.get(iterator);

	//	System.out.println(id + " " + currentX + " " + currentY);
		iterator++;
	}
}
