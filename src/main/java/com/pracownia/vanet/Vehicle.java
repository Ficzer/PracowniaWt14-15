package com.pracownia.vanet;

import javafx.scene.paint.Color;
import lombok.Getter;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Data;

import java.util.ArrayList;
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
	private List<Vehicle> connectedVehicles = new ArrayList<>();
	private List<Event> collectedEvents = new ArrayList<>();

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

	private void updateConnectedVehicles(Map map) {
		for (Vehicle v : map.getVehicles()) {
			if (v == this)
				continue;

			if (distance(this.currentLocation, v.currentLocation) < range) {
				if (!connectedVehicles.contains(v)) {
					connectedVehicles.add(v);
				}
			}
			else if (connectedVehicles.contains(v)) {
				connectedVehicles.remove(v);
			}
		}
	}

	public void update(Map map){
		updateConnectedVehicles(map);

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

	private double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}

	@Override
	public String toString() {
		return "ID:               " + id + '\n' +
			   "Current location: " + currentLocation + '\n' +
			   "Neighbors:        " + connectedVehicles.size() + '\n';
	}
}
