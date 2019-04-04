package com.pracownia.vanet;

import lombok.Data;

@Data
public class Point {

    private double x = 0.0;
    private double y = 0.0;

    public Point(){

    }

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}
