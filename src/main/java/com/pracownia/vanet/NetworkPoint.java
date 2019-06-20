package com.pracownia.vanet;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class NetworkPoint {
    protected int id;
    protected Point currentLocation = new Point();
    protected double range;
    protected List<Vehicle> connectedVehicles = new ArrayList<>();
    protected List<Event> collectedEvents = new ArrayList<>();
    protected List<Event> encounteredEvents = new ArrayList<>();

    public NetworkPoint() {

    }

    public NetworkPoint(int id, Point currentLocation, double range) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.range = range;
    }

    public void updateConnectedPoints(Map map) {
        for (Vehicle v : map.getVehicles()) {
            if (v == this) {
                continue;
            }

            if (distance(this.currentLocation, v.currentLocation) < range) {
                if (!connectedVehicles.contains(v)) {
                    connectedVehicles.add(v);
                }
            } else if (connectedVehicles.contains(v)) {
                connectedVehicles.remove(v);
            }
        }
    }

    public void sendEventsToConnectedPoints() {
        boolean flag;

        for (NetworkPoint connectedVehicle : connectedVehicles) {
            for (Event event : collectedEvents) {
                flag = false;
                for (Event outEvent : connectedVehicle.getCollectedEvents()) {
                    if (event.getId() == outEvent.getId()) {
                        flag = true;
                    }
                }

                if (!flag) {
                    connectedVehicle.getCollectedEvents().add(event);
                    Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                    Logger.log("[" + timeStamp + "] Event " + event.getId() + " shared from Stationary to Vehicle " + connectedVehicle.getId());
                    System.out.println("[" + timeStamp + "] Event " + event.getId() + " shared from Stationary to Vehicle " + connectedVehicle.getId());
                }
            }
        }
    }

    public void update(Map map) {
        updateConnectedPoints(map);
        sendEventsToConnectedPoints();
    }


    protected double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
