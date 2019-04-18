package com.pracownia.vanet;

import lombok.Data;

@Data
public class StationaryNetworkPoint extends NetworkPoint
{
    public StationaryNetworkPoint(int id, Point currentLocation, double range)
    {
        super(id, currentLocation, range);
    }


}
