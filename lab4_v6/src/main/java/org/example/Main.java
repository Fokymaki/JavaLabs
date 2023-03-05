package org.example;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Persist vehicles to database
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vehicleDB");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // Create DAOs
        VehicleDAO vehicleDao = new VehicleDAO(em);
        PlaneDAO planeDao = new PlaneDAO(emf);
        ShipDAO shipDao = new ShipDAO(emf);
        AirportDAO airportDao = new AirportDAO(em);

        // Read vehicles from CSV file
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            String type = values[0];
            String brand = values[1];
            String model = values[2];
            int year = Integer.parseInt(values[3]);
            int maxSpeed = Integer.parseInt(values[4]);
            int altitudeOrDisplacement = Integer.parseInt(values[5]);

            if (type.equals("plane")) {
                int passengers = Integer.parseInt(values[6]);
                String airportIata = values[7];
                Airport airport = airportDao.getByIata(airportIata);
                if (airport == null) {
                    airport = new Airport();
                    airport.setIata(airportIata);
                    airportDao.persist(airport);
                }
                Plane plane = new Plane();
                plane.setBrand(brand);
                plane.setModel(model);
                plane.setYear(year);
                plane.setMaxSpeed(maxSpeed);
                plane.setPassengers(passengers);
                plane.setAltitude(altitudeOrDisplacement);
                plane.setAirport(airport.getIata());
                planeDao.persist(plane);
            } else if (type.equals("ship")) {
                String shipType = values[6];
                Ship ship = new Ship();
                ship.setBrand(brand);
                ship.setModel(model);
                ship.setYear(year);
                ship.setMaxSpeed(maxSpeed);
                ship.setShipType(shipType);
                ship.setDisplacement(altitudeOrDisplacement);
                shipDao.persist(ship);
            } else {
                // Unknown type
                continue;
            }
        }
        reader.close();

        // Display all vehicles
        List<Vehicle> vehicles = vehicleDao.getAll();
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }

        // Update one object and delete another object in a transaction with Dirty Read isolation level
        vehicleDao.updateYearByBrand("Boeing", 2023);
        vehicleDao.deleteByBrand("Hyundai");

        // Select vehicles by brand and airport IATA code
        List<Vehicle> filteredVehicles = vehicleDao.getByBrand("Boeing");
        for (Vehicle vehicle : filteredVehicles) {
            System.out.println(vehicle);
        }
    }
}