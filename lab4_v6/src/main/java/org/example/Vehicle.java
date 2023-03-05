package org.example;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int year;
    private int maxSpeed;

    public Vehicle(String brand, String model, int year, int maxSpeed) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.maxSpeed = maxSpeed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Vehicle() {

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

    @Override
    public String toString() {
        return "Vehicle{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}