package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements Transportable, Serializable {
    private String brand;
    private String model;
    private int year;
    private int maxSpeed;
    private List<String> cargos;

    public Vehicle(String brand, String model, int year, int maxSpeed) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.maxSpeed = maxSpeed;
        this.cargos = new ArrayList<>();
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public List<String> getCargos() {
        return cargos;
    }

    public void addCargo(String cargo) {
        cargos.add(cargo);
    }

    public void removeCargo(String cargo) {
        cargos.remove(cargo);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", maxSpeed=" + maxSpeed +
                ", cargos=" + cargos +
                '}';
    }
}