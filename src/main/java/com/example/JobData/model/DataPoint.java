package com.example.JobData.model;



public class DataPoint {
    private int year;
    private double value;

    public DataPoint(int year, double value) {
        this.year = year;
        this.value = value;
    }

    // Getters and setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
