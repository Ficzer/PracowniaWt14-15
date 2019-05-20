package com.pracownia.vanet;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Simulation implements Runnable {

    public Boolean simulationRunning;
    Thread tr;
    private Map map;
    private List<Circle> circleList;
    private List<Circle> rangeList;
    private List<Label> labelList;
    private List<Circle> stationaryCirclelist;

    public Simulation() {
        map = new Map();
        circleList = new ArrayList<>();
        rangeList = new ArrayList<>();
        labelList = new ArrayList<>();
        stationaryCirclelist = new ArrayList<>();
        this.simulationRunning = true;
        tr = new Thread(this);
    }


    public void run() {
        while (true) {
            if (simulationRunning) {
                updateVehiclesPosition();
                checkVehicleCrossing();
                resetReferences();
                checkVehicleEventSource();
                updateStationaryPoints();

                //showVehiclesConnected();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }

    private void updateVehiclesPosition() {
        int it = 0;

        for (Vehicle vehicle : map.getVehicles()) {
            vehicle.update(map);
            try {
                double vehicleX = vehicle.getCurrentLocation().getX();
                double vehicleY = vehicle.getCurrentLocation().getY();
                circleList.get(it).setCenterX(vehicleX);
                circleList.get(it).setCenterY(vehicleY);

                rangeList.get(it).setCenterX(vehicleX);
                rangeList.get(it).setCenterY(vehicleY);

                //labelList.get(it).setText(String.valueOf(vehicle.getCollectedEvents().size()));

                if (vehicle.trustLevel < 0.3){
                    circleList.get(it).setFill(Color.RED);
                }
                else if (vehicle.getCollectedEvents().size() > 0) {
                    circleList.get(it).setFill(Color.BROWN);
                }
                labelList.get(it).setLayoutX(vehicleX + 7.0);
                labelList.get(it).setLayoutY(vehicleY);
            } catch (IndexOutOfBoundsException e) {
            }
            it++;
        }
    }

    private void checkVehicleCrossing() {
        for (Vehicle vehicle : map.getVehicles()) {
            for (Crossing crossing : map.getCrossings()) {

                if (crossing.getDistanceToCrossing(vehicle) < Crossing.DETECTION_RANGE) {
                    crossing.transportVehicle(vehicle);
                }
            }
        }
    }

    private void resetReferences() {
        for (Crossing crossing : map.getCrossings()) {
            crossing.resetLastTransportedVehicle();
        }
    }

    private void checkVehicleEventSource() {
        checkVehicleEventSourceEncountered();
        checkVehicleEventSourceCollected();
    }

    private void checkVehicleEventSourceCollected() {
        for (Vehicle vehicle : map.getVehicles()) {
            for (EventSource eventSource : map.getEventSources()) {
                if (eventSource.isInRange(vehicle.getCurrentLocation())) {
                    for (Event event : vehicle.getCollectedEvents()) {
                        if (event.getId() == eventSource.getId()) {
                            return;
                        }
                    }

                    vehicle.getCollectedEvents().add(eventSource.getEvent());
                }
            }
        }
    }

    private void checkVehicleEventSourceEncountered() {
        for (Vehicle vehicle : map.getVehicles()) {
            for (EventSource eventSource : map.getEventSources()) {
                if (eventSource.isInRange(vehicle.getCurrentLocation())) {
                    for (Event event : vehicle.getEncounteredEvents()) {
                        if (event.getId() == eventSource.getId()) {
                            return;
                        }
                    }

                    vehicle.getEncounteredEvents().add(eventSource.getEvent());
                    System.out.println("Event Encountered by Vehicle");
                }
            }
        }
    }

    private void updateStationaryPoints() {
        int it = 0;
        for (StationaryNetworkPoint s : map.getStationaryNetworkPoints()) {
            s.update(map);
            s.checkIfChangeVehicleTrustLevel();
            try {
                if (s.getCollectedEvents().size() > 0) { stationaryCirclelist.get(it).setFill(Color.CYAN); }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            it++;
        }
    }

    public void switchOffRangeCircles() {
        for (Circle rangeCircle : rangeList) {
            rangeCircle.setStroke(Color.TRANSPARENT);
        }
    }

    public void switchOnRangeCircles() {
        for (Circle rangeCircle : rangeList) {
            rangeCircle.setStroke(Color.BLACK);
        }
    }


    public void changeVehiclesRanges(double range) {
        for (Vehicle vehicle : map.getVehicles()) {
            vehicle.setRange(range);
        }
        for (Circle rangeCircle : rangeList) {
            rangeCircle.setRadius(range);
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
