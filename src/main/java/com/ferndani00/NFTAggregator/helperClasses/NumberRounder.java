package com.ferndani00.NFTAggregator.helperClasses;

public class NumberRounder {

    public static double rounder(double input) {
        double rounded = Math.round(input * 100);
        return rounded / 100;
    }
}
