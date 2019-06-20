package com.pracownia.vanet;

import lombok.Data;

import java.util.Date;
import java.util.Random;

@Data
public class Crossing {

    public static double DETECTION_RANGE = 1.0;

    private Point location;
    private Route routeA;
    private Route routeB;
    private Vehicle lastTransportedVehicle = new Vehicle();

    public Crossing(){

    }

    public Crossing(Point location, Route routeA, Route routeB) {

        this.location = location;
        this.routeA = routeA;
        this.routeB = routeB;

    }

    public void transportVehicle(Vehicle vehicle)
    {
        if(vehicle == lastTransportedVehicle)
            return;
        lastTransportedVehicle = vehicle;
        Random random = new Random();
        int pom = random.nextInt();
        if(Math.abs(pom % 3) == 0 || Math.abs(pom % 3 ) == 1)
        {
            if(vehicle.getRoute() == routeA)
            {
                vehicle.setRoute(routeB);
            }
            else
            {
                vehicle.setRoute(routeA);
            }

            vehicle.setCurrentLocation(new Point(location.getX(), location.getY()));

            if(Math.abs(pom % 3) == 0)
            {
                vehicle.setDirection(!vehicle.isDirection());
            }

        }

        if (vehicle.getPreviousCrossing() != null && vehicle.getPreviousCrossing() != this.location) {
            double s = Math.sqrt(Math.pow((location.getX() - vehicle.getPreviousCrossing().getX()), 2) + Math.pow(location.getY() - vehicle.getPreviousCrossing().getY(), 2));
            double t = Math.abs(new Date().getTime() - vehicle.getDate().getTime());

            double v = s / (t / 50);
//            System.out.println("Szybkosc auta: " + vehicle.getSpeed());
//            System.out.println("Wyliczona: " + v);
//            System.out.println("Czas: " + t);
//            System.out.println("droga: " + s);

//            System.out.println("pozycja auta: " + vehicle.getPreviousCrossing().getX() + " --- " +vehicle.getPreviousCrossing().getY() );
//            System.out.println("pozycja skrzyzowania: " + location.getX() + " --- " + location.getY());

            if (Math.abs(v - vehicle.getSpeed()) > 0.5) {
                vehicle.setNotSafe("Identified as attacker!");
            } else if(vehicle.trustLevel < 0.3) {
                vehicle.setNotSafe("Identified as attacker!");
            } else {
                //System.out.println("Bezpiecznie.");
            }
        }

        vehicle.setPreviousCrossing(location);
    }

    public double getDistanceToCrossing(Vehicle vehicle)
    {
        return Math.sqrt(Math.pow(location.getX() - vehicle.getCurrentLocation().getX(), 2) +
                Math.pow(location.getY() - vehicle.getCurrentLocation().getY(), 2));
    }

    public void resetLastTransportedVehicle()
    {
        if(getDistanceToCrossing(lastTransportedVehicle) > Crossing.DETECTION_RANGE)
        {
            lastTransportedVehicle = new Vehicle();
        }
    }
}
