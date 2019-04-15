package com.pracownia.vanet;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AntyBogus {
    private static final int CONFIRMATION_LEVEL = 15;
    private static final double TRUST_LEVEL_BONUS = 1.0;
    private static final double THRESH_HOLD = 10E-15;
    private HashMap<String, ObservableList<Vehicle>> eventsByVehicle;
    private List<String> confirmedEvents;

    public AntyBogus(){
        eventsByVehicle = new HashMap<>();
        confirmedEvents = new ArrayList<>();
    }

    public void addEvent(String event, Vehicle vehicle){
        if(!confirmedEvents.contains(event)){
            if(!eventsByVehicle.containsKey(event)){
                eventsByVehicle.put(event, createObservableList(vehicle, event));
            } else {
                eventsByVehicle.get(event).add(vehicle);
            }
        } else {
            updateTrustLevelOf(vehicle);
        }

    }

    private ObservableList<Vehicle> createObservableList(final Vehicle vehicle, final String event) {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        vehicles.add(vehicle);

        vehicles.addListener((ListChangeListener<Vehicle>) c -> checkIfEnoughConfirmations(c.getList(), event));

        return vehicles;
    }

    private void checkIfEnoughConfirmations(ObservableList<? extends Vehicle> list, String event) {
        if(list.size() == CONFIRMATION_LEVEL || checkIfEventConfirmedByTrustedVehicle(list)){
            addEventToConfirm(event);
        }
    }

    private boolean checkIfEventConfirmedByTrustedVehicle(ObservableList<? extends Vehicle> list) {
        for(Vehicle v : list){
            if(Math.abs(v.getTrustLevel() - 100.0) < THRESH_HOLD){
                return true;
            }
        }

        return false;
    }

    private void updateTrustLevelOf(List<Vehicle> vehicleList){
        for(Vehicle vehicle : vehicleList){
            updateTrustLevelOf(vehicle);
        }
    }

    private void updateTrustLevelOf(Vehicle vehicle){
        double currentTrustLevel = vehicle.getTrustLevel();

        if(currentTrustLevel + TRUST_LEVEL_BONUS < 100.0){
            vehicle.setTrustLevel(currentTrustLevel + TRUST_LEVEL_BONUS);
        } else if( currentTrustLevel < 100.0){
            vehicle.setTrustLevel(100.0);
        }
    }

    private void addEventToConfirm(String event){
        updateTrustLevelOf(eventsByVehicle.get(event));

        eventsByVehicle.remove(event);

        confirmedEvents.add(event);
    }
}