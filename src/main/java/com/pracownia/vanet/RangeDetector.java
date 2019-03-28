package com.pracownia.vanet;

import com.pracownia.vanet.net.Rsu;
import com.pracownia.vanet.net.Vanet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RangeDetector {

    public static void checkForPossibleConnections(List<Vehicle> vehicles, List<Rsu> rsus) {
        for (int i = 0; i < vehicles.size(); ++i) {
            for (int j = i + 1; j < vehicles.size(); ++j) {
                if(vehicles.get(i).getCoordinates().euclideanDistance(
                        vehicles.get(j).getCoordinates()) < Vanet.OBU_RANGE) {
                    vehicles.get(i).getObu().connect(vehicles.get(j).getObu());
                } else {
                    vehicles.get(i).getObu().disconnect(vehicles.get(j).getObu());
                }
            }
            for (Rsu rsu : rsus) {
                if(vehicles.get(i).getCoordinates().euclideanDistance(
                        rsu.getCoordinates()) < Vanet.RSU_RANGE) {
                    vehicles.get(i).getObu().connect(rsu);
                } else {
                    vehicles.get(i).getObu().disconnect(rsu);
                }
            }
        }
    }
}
