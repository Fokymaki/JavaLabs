package com.example.lab7_v6;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vehicle")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int maxSpeed;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, int year, int maxSpeed) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.maxSpeed = maxSpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", maxSpeed=" + maxSpeed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && year == vehicle.year && maxSpeed == vehicle.maxSpeed && Objects.equals(brand, vehicle.brand) && Objects.equals(model, vehicle.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, year, maxSpeed);
    }
}