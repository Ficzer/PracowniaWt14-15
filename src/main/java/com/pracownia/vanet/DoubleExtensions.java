package com.pracownia.vanet;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DoubleExtensions {
    private static final double EPSILON = 1E-6;

    public static boolean areDoublesEqual(double lhs, double rhs) {
        return Math.abs(lhs - rhs) < EPSILON;
    }

    public static boolean areDoublesEqual(double lhs, double rhs, double epsilon) {
        return Math.abs(lhs - rhs) < epsilon;
    }
}