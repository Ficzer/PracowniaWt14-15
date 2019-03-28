package com.pracownia.vanet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Point {
    private double x;
    private double y;

    public double euclideanDistance(Point to) {
        return Math.sqrt((x - to.getX()) * (x - to.getX()) + (y - to.getY()) * (y - to.getY()));
    }

    @Override
    public int hashCode() {
        return (int)(x * 16661 + y);
    }

    @Override
    public boolean equals(Object obj) {
        if(Objects.isNull(obj)) {
            return false;
        } else if (!(obj instanceof Point)) {
            return false;
        } else {
            Point other = (Point)obj;
            return DoubleExtensions.areDoublesEqual(this.x, other.getX()) &&
                    DoubleExtensions.areDoublesEqual(this.y, other.getY());
        }
    }
}
