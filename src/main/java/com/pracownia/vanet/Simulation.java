package com.pracownia.vanet;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Simulation implements Runnable{

	private Map map;
	private List<Circle> circleList;
	private List<Circle> rangeList;
	private List<Label> labelList;
	private List<Circle> stationaryCirclelist;

	Thread tr;
	public Simulation() {
		map = new Map();
		circleList = new ArrayList<>();
		rangeList = new ArrayList<>();
		labelList = new ArrayList<>();
		stationaryCirclelist = new ArrayList<>();

		tr = new Thread(this);
	}


	public void run(){
		while (true) {
			updateVehiclesPosition();
			checkVehicleCrossing();
			resetReferences();
			checkVehicleEventSource();
			updateStationaryPoints();

			//showVehiclesConnected();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
			}
		}
	}

	private void updateVehiclesPosition() {

		int it = 0;

		for (Vehicle vehicle : map.getVehicles()) {
			vehicle.update(map);
			try
			{
				circleList.get(it).setCenterX(vehicle.getCurrentLocation().getX());
				circleList.get(it).setCenterY(vehicle.getCurrentLocation().getY());
				rangeList.get(it).setCenterX(vehicle.getCurrentLocation().getX());
				rangeList.get(it).setCenterY(vehicle.getCurrentLocation().getY());
				//labelList.get(it).setText(String.valueOf(vehicle.getCollectedEvents().size()));
				if(vehicle.getCollectedEvents().size() > 0)
					circleList.get(it).setFill(Color.BROWN);
				labelList.get(it).setLayoutX(vehicle.getCurrentLocation().getX() + 7.0);
				labelList.get(it).setLayoutY(vehicle.getCurrentLocation().getY());
			}
			catch (IndexOutOfBoundsException e)
			{
				//e.printStackTrace();
			}
			it++;
		}
	}

	private void checkVehicleCrossing()
	{
		for (Vehicle vehicle : map.getVehicles()) {
			for(Crossing crossing : map.getCrossings()) {

				if(crossing.getDistanceToCrossing(vehicle) < Crossing.DETECTION_RANGE){
					crossing.transportVehicle(vehicle);
				}
			}
		}
	}

	private void resetReferences()
	{
		for(Crossing crossing : map.getCrossings())
		{
			crossing.resetLastTransportedVehicle();
		}
	}

	private void checkVehicleEventSource()
	{
		for (Vehicle vehicle : map.getVehicles())
		{
			for (EventSource eventSource : map.getEventSources())
			{
				if(eventSource.isInRange(vehicle.getCurrentLocation()))
				{
					for (Event event : vehicle.getCollectedEvents())
					{
						if(event.getId() == eventSource.getId())
						{
							return;
						}
					}

					vehicle.getCollectedEvents().add(eventSource.getEvent());
				}
			}
		}
	}

	private void updateStationaryPoints()
	{
		int it = 0;
		for(StationaryNetworkPoint s : map.getStationaryNetworkPoints())
		{
			s.update(map);
			s.checkIfChangeVehicleTrustLevel();
			try
			{
				if (s.getCollectedEvents().size() > 0)
					stationaryCirclelist.get(it).setFill(Color.CYAN);
			}
			catch (IndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
			it++;
		}
	}

	public void switchOffRangeCircles()
	{
		for(Circle rangeCircle : rangeList)
		{
			rangeCircle.setStroke(Color.TRANSPARENT);
		}
	}

	public void switchOnRangeCircles()
	{
		for(Circle rangeCircle : rangeList)
		{
			rangeCircle.setStroke(Color.BLACK);
		}
	}

/*
	private void showVehiclesConnected(){
		int it = 0;
		for (Vehicle vehicle : map.getVehicles()) {
			for (Vehicle vehicle2 : map.getVehicles()) {
				if(vehicle.id != vehicle2.id){
					double xcoord = Math.abs (vehicle.getCurrentX() - vehicle2.getCurrentX());
					double ycoord = Math.abs (vehicle.getCurrentY() - vehicle2.getCurrentY());
					double distance = Math.sqrt(ycoord*ycoord + xcoord * xcoord);
					if(distance<=vehicle.getRange()) {
						circleList.get(it).setFill(Color.GREEN);
						break;
					}
					else circleList.get(it).setFill(Color.BLACK);
				}
			}
			it++;
		}
	}*/

}
