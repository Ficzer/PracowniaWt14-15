package com.pracownia.vanet;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StationaryNetworkPoint extends NetworkPoint
{
    private final static double TRUST_LEVEL_INCREASE = 0.1;
    private final static double TRUST_LEVEL_DECREASE = 0.4;

    public StationaryNetworkPoint(int id, Point currentLocation, double range)
    {
        super(id, currentLocation, range);
    }


    public void checkIfChangeVehicleTrustLevel() {
        for(Vehicle v : this.connectedVehicles){
            if(AntyBogus.vehiclesToIncreaseTrustLevel.contains(v)){
                increaseVehicleTrustLevel(v);
                AntyBogus.vehiclesToIncreaseTrustLevel.remove(v);
                Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                Logger.log("[" + timeStamp + "] Increased trust level of Vehicle " + v.id);
                System.out.println("[" + timeStamp + "] Increased trust level of Vehicle " + v.id);
            } else if(AntyBogus.vehiclesToDecreaseTrustLevel.contains(v)){
                decreaseVehicleTrustLevel(v);
                AntyBogus.vehiclesToDecreaseTrustLevel.remove(v);
                Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                Logger.log("[" + timeStamp + "] Decreased trust level of Vehicle " + v.id);
                System.out.println("[" + timeStamp + "] Decreased trust level of Vehicle " + v.id);
            }
        }
    }

    private void increaseVehicleTrustLevel(Vehicle vehicle) {
        double previousTrustLevel = vehicle.getTrustLevel();

        vehicle.setTrustLevel(previousTrustLevel + TRUST_LEVEL_INCREASE);
    }

    private void decreaseVehicleTrustLevel(Vehicle vehicle) {
        double previousTrustLevel = vehicle.getTrustLevel();

        vehicle.setTrustLevel(previousTrustLevel - TRUST_LEVEL_DECREASE);
    }

}
