package com.pracownia.vanet;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class AntyBogus {
    private static final int CONFIRMATION_LEVEL = 2;
    private static final double TRUST_LEVEL_BONUS = 1.0;
    private static final double THRESH_HOLD = 10E-15;
    private static ConcurrentMap<Event, ObservableList<Vehicle>> eventsByVehicle;
    public static ConcurrentMap<Event, List<Vehicle>> modifiedTrustLevelVehicles;
    private static ScheduledExecutorService cleanEventsTaskExecutor;
    public static List<Vehicle> vehiclesToIncreaseTrustLevel;
    public static List<Vehicle> vehiclesToDecreaseTrustLevel;

    static {
        eventsByVehicle = new ConcurrentHashMap<>();
        modifiedTrustLevelVehicles = new ConcurrentHashMap<>();
        vehiclesToIncreaseTrustLevel = new ArrayList<>();
        vehiclesToDecreaseTrustLevel = new ArrayList<>();

        cleanEventsTaskExecutor = Executors.newScheduledThreadPool(1);
        cleanEventsTaskExecutor.scheduleAtFixedRate(createCleaningEventsTask(),
                30,
                30,
                    TimeUnit.SECONDS
                );
    }

    private static Runnable createCleaningEventsTask() {
        return () -> {
            Date currentDate = new Date(System.currentTimeMillis());
            for (Event e : eventsByVehicle.keySet()) {
                if (currentDate.getTime() >= (e.getEventDate().getTime() + TimeUnit.SECONDS.toMillis(15))) {
                    if(eventsByVehicle.get(e).size() < CONFIRMATION_LEVEL){
                        addVehicleToDecrease(eventsByVehicle.get(e), e);
                        eventsByVehicle.remove(e);
                    }
                }
            }
        };
    }

    public static void addEvent(Event event, Vehicle vehicle) {
        if (!eventsByVehicle.containsKey(event)) {
            eventsByVehicle.put(event, createObservableList(vehicle, event));
            if(!modifiedTrustLevelVehicles.containsKey(event)){
                modifiedTrustLevelVehicles.put(event, new ArrayList<>());
            }
        } else if (!eventsByVehicle.get(event).contains(vehicle)) {
            eventsByVehicle.get(event).add(vehicle);
        }
    }

    private static ObservableList<Vehicle> createObservableList(final Vehicle vehicle, final Event event) {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        vehicles.add(vehicle);

        vehicles.addListener((ListChangeListener<Vehicle>) c -> checkIfEnoughConfirmations(c.getList(), event));

        return vehicles;
    }

    private static void checkIfEnoughConfirmations(ObservableList<? extends Vehicle> list, Event event) {
        if (list.size() >= CONFIRMATION_LEVEL || checkIfEventConfirmedByTrustedVehicle(list)) {
            addVehicleToIncrease(list, event);
        }
    }

    private static boolean checkIfEventConfirmedByTrustedVehicle(ObservableList<? extends Vehicle> list) {
        for (Vehicle v : list) {
            if (Math.abs(v.getTrustLevel() - 100.0) < THRESH_HOLD) {
                return true;
            }
        }

        return false;
    }


    private synchronized static void addVehicleToIncrease(ObservableList<? extends Vehicle> vehicleList, Event event) {
        for (Vehicle v : vehicleList) {
            if (!vehiclesToIncreaseTrustLevel.contains(v)) {
                if(!modifiedTrustLevelVehicles.get(event).contains(v)) {
                    vehiclesToIncreaseTrustLevel.add(v);
                    modifiedTrustLevelVehicles.get(event).add(v);
                }
            }
        }
    }

    private synchronized static void addVehicleToDecrease(ObservableList<? extends Vehicle> vehicleList, Event event) {
        for (Vehicle v : vehicleList) {
            if (!vehiclesToDecreaseTrustLevel.contains(v)) {
                if(!modifiedTrustLevelVehicles.get(event).contains(v)) {
                    vehiclesToDecreaseTrustLevel.add(v);
                    modifiedTrustLevelVehicles.get(event).add(v);
                }
            }
        }
    }
}