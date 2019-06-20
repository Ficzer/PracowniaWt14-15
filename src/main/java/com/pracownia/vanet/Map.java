package com.pracownia.vanet;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Przechoowuje samochody i ich powiązania z trasami.
 */

@Data
public class Map {

	private double width = 1000.0;
	private double height = 900.0;

	private static int fakeCarId = -666;
	private static int fakeEventId = -1;

	private List<Route> routes;
	private ObservableList<Vehicle> vehicles;
	private List<Crossing> crossings;
	private List<EventSource> eventSources;
	private List<StationaryNetworkPoint> stationaryNetworkPoints;
	private ObservableList<Vehicle> hackers;


	public Map(){

		routes = new ArrayList<>();
		vehicles = FXCollections.observableArrayList();

//		vehicles.addListener((ListChangeListener.Change<? extends Vehicle> change) -> {
//			while (change.next()) {
//				for (Vehicle vehicle : vehicles) {
//					if(!vehicle.safe){
//						if(!hackers.contains(vehicle))
//							hackers.add(vehicle);
//					}else{
//						if(hackers.contains(vehicle))
//							hackers.remove(vehicle);
//					}
//				}
////				hackersvehicles.stream().filter(x->!x.safe).collect(Collectors.toList()));
//			}
//		});
		
		hackers = FXCollections.observableArrayList();
		crossings = new ArrayList<>();
		eventSources = new ArrayList<>();
		stationaryNetworkPoints = new ArrayList<>();
		initMap();

	}

	public List<Integer> deleteUnsafeVehicles() {

		List<Integer> result = new ArrayList<>();
		for(int i = 0; i < vehicles.size(); i++) {
			if(vehicles.get(i).safe == false) {
				result.add(i);
				vehicles.remove(i);
				i--;
			}
		}

		return result;
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

		eventSources.add(new EventSource(1, "Car Accident", "Serious Car Accident",
				new Point(500.0, 410.0), new Date(), 20.0, EventType.CAR_ACCIDENT));

		eventSources.add(new EventSource(2, "Car Accident", "Serious Car Accident",
				new Point(750.0, 610.0), new Date(), 20.0, EventType.CAR_ACCIDENT));
	}

	public void addVehicles(int amount)
	{
		Random random = new Random();

		for (int i=0; i<amount; i++)
		{
			vehicles.add(new Vehicle(routes.get(i%5), i, 40.0, random.nextDouble() / 2.0 + 2));
		}
	}

	public Vehicle addCopy() {
		int r = new Random().nextInt(vehicles.size());
		Vehicle me = new Vehicle(vehicles.get(r).getRoute(), vehicles.get(r).getId(), vehicles.get(r).getRange(), vehicles.get(r).getSpeed());
		vehicles.add(me);

		return me;
	}

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
