package org.example;

public class Plane extends Vehicle {
    private int passengers;
    private int altitude;

    public Plane(String brand, String model, int year, int maxSpeed, int passengers, int altitude) {
        super(brand, model, year, maxSpeed);
        this.passengers = passengers;
        this.altitude = altitude;
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
                ", cargos=" + getCargos() +
                '}';
    }
}