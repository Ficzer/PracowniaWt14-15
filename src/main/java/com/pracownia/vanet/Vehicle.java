package com.pracownia.vanet;

import lombok.Data;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Data
public class Vehicle extends NetworkPoint{

	int id;
	double currentX;
	double currentY;
	Route route;
	int iterator;
	@Getter
	@Setter
	public double trustLevel;
	private double speed;
	private boolean direction = true; // True if from starting point to end point
	private List<StationaryNetworkPoint> connectedPoints = new ArrayList<>();

	public Vehicle()
	{
		super();
		route = new Route();
		trustLevel = 50;
		currentLocation = new Point();
	}

	public Vehicle(Route route, int id, double range, double speed){
		super();
		this.route = route;
		this.id = id;
		this.range = range;
		this.speed = speed + 0.001;
		trustLevel = 50;
		this.currentLocation = new Point(route.getStartPoint().getX(), route.getStartPoint().getY());
	}

	@Override
	public void updateConnectedPoints(Map map)
	{
		for (Vehicle v : map.getVehicles()) {
			if (v == this)
			{
				continue;
			}

			if (distance(this.currentLocation, v.currentLocation) < range) {
				if (!connectedVehicles.contains(v)) {
					connectedVehicles.add(v);
				}
			}
			else if (connectedVehicles.contains(v)) {
				connectedVehicles.remove(v);
			}
		}

		for (StationaryNetworkPoint s : map.getStationaryNetworkPoints())
		{
			if (distance(this.currentLocation, s.getCurrentLocation()) < range)
			{
				if (!connectedPoints.contains(s)) {
					connectedPoints.add(s);
				}
			}
			else
			{
				if (isPointInList(s, connectedPoints))
				{
					connectedPoints.remove(s);
				}
			}
		}


	}

	public void sendEventsToConnectedPoints()
	{
		boolean flag;

		for (NetworkPoint connectedVehicle : connectedVehicles)
		{
			for (Event event : collectedEvents)
			{
				flag = false;
				for (Event outEvent : connectedVehicle.getCollectedEvents())
				{
					if(event.getId() == outEvent.getId())
					{
						flag = true;
					}
				}

				if(!flag && this.trustLevel >= 50)
				{
					connectedVehicle.getCollectedEvents().add(event);
					System.out.println("1Event sharedd");
				}
			}
		}

		for (NetworkPoint connectedPoint : connectedPoints)
		{
			for (Event event : collectedEvents)
			{
				flag = false;
				for (Event outEvent : connectedPoint.getCollectedEvents())
				{
					if(event.getId() == outEvent.getId())
					{
						flag = true;
					}
				}

				if(!flag && this.trustLevel >= 50)
				{
					connectedPoint.getCollectedEvents().add(event);
					System.out.println("2Event shared");
				}
			}
		}
	}

	@Override
	public void update(Map map){
		updateConnectedPoints(map);
		sendEventsToConnectedPoints();

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

	public boolean isPointInList(StationaryNetworkPoint point, List<StationaryNetworkPoint> list)
	{
		boolean result = false;
		for (StationaryNetworkPoint s : list)
		{
			if(s.getId() == point.getId())
			{
				result = true;
			}

		}
		return result;
	}

	@Override
	public String toString() {
		return "ID:               " + id + '\n' +
			   "Current location: " + currentLocation + '\n' +
			   "Neighbors:        " + connectedVehicles.size() + '\n';
	}
}
