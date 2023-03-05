package com.example.lab7_v6;

import javax.persistence.*;

@Entity
@DiscriminatorValue("plane")
public class Plane extends Vehicle {
    @Column(nullable = false)
    private int passengers;
    @Column(nullable = false)
    private int altitude;

    @Column(nullable = false)
    private String airport;

    public Plane(String brand, String model, int year, int maxSpeed, int passengers, int altitude, String airport) {
        super(brand, model, year, maxSpeed);
        this.passengers = passengers;
        this.altitude = altitude;
        this.airport = airport;
    }

    public Plane() {

    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public int getPassengers() {
        return passengers;
    }

    public int getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "brand='" + getBrand() + '\'' +
                ", model='" + getModel() + '\'' +
                ", year=" + getYear() +
                ", maxSpeed=" + getMaxSpeed() +
                ", passengers=" + passengers +
                ", altitude=" + altitude +
                '}';
    }
}