public class Ship extends Vehicle {
    private String shipType;
    private int displacement;

    public Ship(String brand, String model, int year, int maxSpeed, String passengers, int displacement) {
        super(brand, model, year, maxSpeed);
        this.shipType = passengers;
        this.displacement = displacement;
    }

    public String getShipType() {
        return shipType;
    }

    public int getAltitude() {
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
                ", cargos=" + getCargos() +
                '}';
    }
}
