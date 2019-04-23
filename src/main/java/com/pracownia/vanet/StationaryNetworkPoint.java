package com.pracownia.vanet;

import lombok.Data;

@Data
public class StationaryNetworkPoint extends NetworkPoint
{
    private final static double TRUST_LEVEL_INCREASE = 0.5;
    private final static double TRUST_LEVEL_DECREASE = 1.0;

    public StationaryNetworkPoint(int id, Point currentLocation, double range)
    {
        super(id, currentLocation, range);
    }


    public void checkIfChangeVehicleTrustLevel() {
        for(Vehicle v : this.connectedVehicles){
            if(AntyBogus.vehiclesToIncreaseTrustLevel.contains(v)){
                increaseVehicleTrustLevel(v);
            } else if(AntyBogus.vehiclesToDecreaseTrustLevel.contains(v)){
                decreaseVehicleTrustLevel(v);
            }
        }
    }

    private void increaseVehicleTrustLevel(Vehicle vehicle) {
        double previousTrustLevel = vehicle.getTrustLevel();

        vehicle.setTrustLevel(previousTrustLevel + TRUST_LEVEL_INCREASE);
    }

    private void decreaseVehicleTrustLevel(Vehicle vehicle) {
        double previousTrustLevel = vehicle.getTrustLevel();

        vehicle.setTrustLevel(previousTrustLevel - TRUST_LEVEL_INCREASE);
    }


}
