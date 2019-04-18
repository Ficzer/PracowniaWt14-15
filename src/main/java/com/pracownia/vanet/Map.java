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



	public Map(){

		routes = new ArrayList<>();
		vehicles = new ArrayList<>();
		crossings = new ArrayList<>();
		eventSources = new ArrayList<>();
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


		//vehicles.add(new Vehicle(routes.get(0), 0, 10.0, 0.4));
		//vehicles.add(new Vehicle(routes.get(1), 1, 10.0, 1.));
		//vehicles.add(new Vehicle(routes.get(1), 2, 20., 1.));

		eventSources.add(new EventSource(0, "Car Accident", "Serious Car Accident",
				new Point(250.0, 210.0), new Date(), 20.0, EventType.CAR_ACCIDENT));
	}

	public void addVehicles(int amount)
	{
		Random random = new Random();

		for (int i=0; i<amount; i++)
		{
			vehicles.add(new Vehicle(routes.get(i%5), i, 40.0, random.nextDouble()));
		}
	}


}
