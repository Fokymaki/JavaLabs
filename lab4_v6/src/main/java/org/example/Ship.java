package org.example;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ship")
public class Ship extends Vehicle {
    private String shipType;
    private int displacement;

    public Ship(String brand, String model, int year, int maxSpeed, String passengers, int displacement) {
        super(brand, model, year, maxSpeed);
        this.shipType = passengers;
        this.displacement = displacement;
    }

    public Ship() {

    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public String getShipType() {
        return shipType;
    }

    public int getDisplacement() {
        return displacement;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "brand='" + getBrand() + '\'' +
                ", model='" + getModel() + '\'' +
                ", year=" + getYear() +
                ", maxSpeed=" + getMaxSpeed() +
                ", shipType=" + shipType +
                ", displacement=" + displacement +
                '}';
    }
}
