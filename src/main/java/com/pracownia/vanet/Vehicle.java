package com.pracownia.vanet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends NetworkPoint {

    @Getter
    @Setter
    public double trustLevel;
    int id;
    double currentX;
    double currentY;
    Route route;
    int iterator;
    public double speed;
    private boolean direction = true; // True if from starting point to end point
    public List<StationaryNetworkPoint> connectedPoints = new ArrayList<>();

    public Date date;
    public Point previousCrossing;
    public boolean safe = true;

    public double getSpeed() {
        return speed;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public Point getPreviousCrossing() {
        return previousCrossing;
    }

    public void setPreviousCrossing(Point previousCrossing) {
        this.previousCrossing = previousCrossing;
        this.setDate(new Date());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Vehicle() {
        super();
        route = new Route();
        trustLevel = 0.5;
        currentLocation = new Point();
    }

    public void setNotSafe(String mssg) {
        if(this.safe == true)
        {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            Logger.log("[" + timeStamp + "] Vehicle "+ id + " : " + mssg);
            System.out.println("[" + timeStamp + "] Vehicle "+ id + " : " + mssg);
            this.safe = false;
        }
    }

    public Vehicle(Route route, int id, double range, double speed) {
        super();
        this.route = route;
        this.id = id;
        this.range = range;
        this.speed = speed + 0.001;
        trustLevel = 0.5;
        this.currentLocation = new Point(route.getStartPoint().getX(), route.getStartPoint().getY());
    }

    @Override
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

        for (StationaryNetworkPoint s : map.getStationaryNetworkPoints()) {
            if (distance(this.currentLocation, s.getCurrentLocation()) < range) {
                if (!connectedPoints.contains(s)) {
                    connectedPoints.add(s);
                }
            } else {
                if (isPointInList(s, connectedPoints)) {
                    connectedPoints.remove(s);
                }
            }
        }
        if (!connectedPoints.isEmpty()) {
            for (Event event : encounteredEvents) {
                AntyBogus.addEvent(event, this);
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

                if (!flag && this.trustLevel >= 0.5) {
                    connectedVehicle.getCollectedEvents().add(event);
                    Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                    Logger.log("[" + timeStamp + "] Event " + event.getId() + " shared from Vehicle " + this.getId() + " to Vehicle " + connectedVehicle.getId());
                    System.out.println("[" + timeStamp + "] Event " + event.getId() + " shared from Vehicle " + this.getId() + " to Vehicle " + connectedVehicle.getId());
                }
            }
        }

        for (NetworkPoint connectedPoint : connectedPoints) {
            for (Event event : collectedEvents) {
                flag = false;
                for (Event outEvent : connectedPoint.getCollectedEvents()) {
                    if (event.getId() == outEvent.getId()) {
                        flag = true;
                    }
                }

                if (!flag && this.trustLevel >= 0.5) {
                    connectedPoint.getCollectedEvents().add(event);
                    Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                    Logger.log("[" + timeStamp + "] Event " + event.getId() + " shared from Vehicle " + this.getId() + " to Stationary");
                    System.out.println("[" + timeStamp + "] Event " + event.getId() + " shared from Vehicle " + this.getId() + " to Stationary");
                }
            }
        }
    }

    @Override
    public void update(Map map) {
        updateConnectedPoints(map);
        sendEventsToConnectedPoints();

        double distanceToEndPoint = Math.sqrt(Math.pow(route.getEndPoint().getX() - currentLocation.getX(), 2) +
                Math.pow(route.getEndPoint().getY() - currentLocation.getY(), 2));

        double cos = (route.getEndPoint().getX() - currentLocation.getX()) / distanceToEndPoint;
        double sin = (route.getEndPoint().getY() - currentLocation.getY()) / distanceToEndPoint;

        double distanceToStart;

        if (direction) {
            distanceToStart = Math.sqrt(Math.pow(currentLocation.getX() - route.getStartPoint().getX(), 2) +
                    Math.pow(currentLocation.getY() - route.getStartPoint().getY(), 2));
            currentLocation.setX(currentLocation.getX() + cos * speed);
            currentLocation.setY(currentLocation.getY() + sin * speed);
        } else {
            distanceToStart = Math.sqrt(Math.pow(currentLocation.getX() - route.getEndPoint().getX(), 2) +
                    Math.pow(currentLocation.getY() - route.getEndPoint().getY(), 2));

            currentLocation.setX(currentLocation.getX() - cos * speed);
            currentLocation.setY(currentLocation.getY() - sin * speed);
        }

        if (distanceToStart >= route.getDistance()) {
            direction = !direction;
        }

        //System.out.println(this.toString());
    }

    public boolean isPointInList(StationaryNetworkPoint point, List<StationaryNetworkPoint> list) {
        boolean result = false;
        for (StationaryNetworkPoint s : list) {
            if (s.getId() == point.getId()) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "ID:\t" + id + '\t' +
                "safe: " + safe;
//                "Neighbours:\t" + connectedVehicles.size() + '\t' +
//                "Current location:\t" + currentLocation;
    }

    public void addFakeEvent(EventSource eventSource)
    {
        AntyBogus.addEvent(eventSource.getEvent() ,this);
        this.getEncounteredEvents().add(eventSource.getEvent());
    }
}
