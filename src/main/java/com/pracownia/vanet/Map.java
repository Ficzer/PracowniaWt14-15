package com.pracownia.vanet;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Przechoowuje samochody i ich powiązania z trasami.
 */

@Data
public class Map {

	private double width = 1000.0;
	private double height = 900.0;

	private List<Route> routes;
	private List<Vehicle> vehicles;
	private List<Crossing> crossings;
	private List<EventSource> eventSources;
	private List<StationaryNetworkPoint> stationaryNetworkPoints;



	public Map(){

		routes = new ArrayList<>();
		vehicles = new ArrayList<>();
		crossings = new ArrayList<>();
		eventSources = new ArrayList<>();
		stationaryNetworkPoints = new ArrayList<>();
		initMap();

	}

	private void initMap()
	{
		routes.add(new Route(200.0, 100.0, 200.0, 700.0));
		routes.add(new Route(400.0, 100.0, 400.0, 700.0));
		routes.add(new Route(600.0, 100.0, 600.0, 700.0));
		routes.add(new Route(800.0, 100.0, 800.0, 700.0));
		routes.add(new Route(100.0, 200.0, 900.0, 200.0));
		routes.add(new Route(100.0, 400.0, 900.0, 400.0));
		routes.add(new Route(100.0, 600.0, 900.0, 600.0));
		crossings.add(new Crossing(new Point(200.0, 200.0), routes.get(0), routes.get(4)));
		crossings.add(new Crossing(new Point(200.0, 400.0), routes.get(0), routes.get(5)));
		crossings.add(new Crossing(new Point(200.0, 600.0), routes.get(0), routes.get(6)));
		crossings.add(new Crossing(new Point(400.0, 200.0), routes.get(1), routes.get(4)));
		crossings.add(new Crossing(new Point(400.0, 400.0), routes.get(1), routes.get(5)));
		crossings.add(new Crossing(new Point(400.0, 600.0), routes.get(1), routes.get(6)));
		crossings.add(new Crossing(new Point(600.0, 200.0), routes.get(2), routes.get(4)));
		crossings.add(new Crossing(new Point(600.0, 400.0), routes.get(2), routes.get(5)));
		crossings.add(new Crossing(new Point(600.0, 600.0), routes.get(2), routes.get(6)));
		crossings.add(new Crossing(new Point(800.0, 200.0), routes.get(3), routes.get(4)));
		crossings.add(new Crossing(new Point(800.0, 400.0), routes.get(3), routes.get(5)));
		crossings.add(new Crossing(new Point(800.0, 600.0), routes.get(3), routes.get(6)));

		stationaryNetworkPoints.add(new StationaryNetworkPoint(0, new Point(480.0, 210.0), 30.0));
		stationaryNetworkPoints.add(new StationaryNetworkPoint(1, new Point(260.0, 610.0), 30.0));
		stationaryNetworkPoints.add(new StationaryNetworkPoint(2, new Point(480.0, 610.0), 30.0));

		eventSources.add(new EventSource(0, "Car Accident", "Serious Car Accident",
				new Point(250.0, 210.0), new Date(), 20.0, EventType.CAR_ACCIDENT));
	}

	public void addVehicles(int amount)
	{
		Random random = new Random();

		for (int i=0; i<amount; i++)
		{
			vehicles.add(new Vehicle(routes.get(i%5), i, 40.0, random.nextDouble() / 2.0 + 2));
		}
	}

	private static int fakeCarId = -666;
	private static int fakeEventId = -1;

	public void addFakeVehicle(String nameEvent)
	{
		Random random = new Random();
		int x = (int)(random.nextDouble() * 1000);
		int y = (int)(random.nextDouble() * 1000);
		Vehicle vehicle = new Vehicle(routes.get(99%5), fakeCarId, 40.0, random.nextDouble() / 2.0 + 2);
		EventSource eventSource = new EventSource(fakeEventId, nameEvent, "Fake Car Accident",
				new Point(x, y), new Date(), 20.0, EventType.CAR_ACCIDENT);
		vehicle.addFakeEvent(eventSource);
		vehicles.add(vehicle);
		fakeCarId--;
		fakeEventId--;
	}

}
