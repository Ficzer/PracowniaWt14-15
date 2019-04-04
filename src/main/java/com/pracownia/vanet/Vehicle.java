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


@Data
public class Vehicle {

	private int id;
	private Point currentLocation;
	private Route route;
	private double range;
	private int iterator;
	private double speed;
	private boolean direction = true; // True if from starting point to end point

	public Vehicle()
	{
		route = new Route();
		currentLocation = new Point();
	}

	public Vehicle(Route route, int id, double range, double speed){
		this.route = route;
		this.id = id;
		this.range = range;
		this.speed = speed + 0.001;
		this.currentLocation = new Point(route.getStartPoint().getX(), route.getStartPoint().getY());
	}

	public void update(){

		double distanceToEndPoint = Math.sqrt(Math.pow(route.getEndPoint().getX() - currentLocation.getX(), 2) +
				Math.pow(route.getEndPoint().getY() - currentLocation.getY(), 2));

		double cos = (route.getEndPoint().getX() - currentLocation.getX()) / distanceToEndPoint;
		double sin = (route.getEndPoint().getY() - currentLocation.getY()) / distanceToEndPoint;

		double distanceToStart;

		if(direction)
		{
			distanceToStart = Math.sqrt(Math.pow(currentLocation.getX()-route.getStartPoint().getX(), 2) +
					Math.pow(currentLocation.getY()-route.getStartPoint().getY(), 2));
			currentLocation.setX(currentLocation.getX() + cos * speed);
			currentLocation.setY(currentLocation.getY() + sin * speed);
		}
		else
		{
			distanceToStart = Math.sqrt(Math.pow(currentLocation.getX()-route.getEndPoint().getX(), 2) +
					Math.pow(currentLocation.getY()-route.getEndPoint().getY(), 2));

			currentLocation.setX(currentLocation.getX() - cos * speed);
			currentLocation.setY(currentLocation.getY() - sin * speed);

		}

		if(distanceToStart >= route.getDistance())
		{
			direction = !direction;
		}
	}
}
