package com.pracownia.vanet;

import lombok.Data;

import java.util.Random;

@Data
public class Crossing {

    public static double DETECTION_RANGE = 5.0;

    private Point location;
    private Route routeA;
    private Route routeB;

    public Crossing(){

    }

    public Crossing(Point location, Route routeA, Route routeB) {

        this.location = location;
        this.routeA = routeA;
        this.routeB = routeB;

    }

    public void transportVehicle(Vehicle vehicle)
    {
        Random random = new Random();
        int pom = random.nextInt();
        if(pom % 3 == 0 || pom % 3 == 1)
        {
            if(vehicle.getRoute() == routeA)
            {
                vehicle.setRoute(routeB);
            }
            else
            {
                vehicle.setRoute(routeA);
            }

            //vehicle.setCurrentLocation(location.getX(), location.getY());
        }

    }
}
