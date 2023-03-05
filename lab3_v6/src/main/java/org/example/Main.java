package org.example;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        if (args.length != 1) {
            System.err.println("Usage: java Main input_file.csv");
            System.exit(1);
        }

        String inputFileName = args[0];
        String logFileName = "log.txt";

        List<Vehicle> vehicles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length < 5) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                String type = tokens[0].trim();
                String brand = tokens[1].trim();
                String model = tokens[2].trim();
                int year = Integer.parseInt(tokens[3].trim());
                int maxSpeed = Integer.parseInt(tokens[4].trim());

                if (type.equalsIgnoreCase("plane")) {
                    if (tokens.length < 7) {
                        System.err.println("Invalid line: " + line);
                        continue;
                    }

                    int passengers = Integer.parseInt(tokens[5].trim());
                    int altitude = Integer.parseInt(tokens[6].trim());

                    Plane plane = new Plane(brand, model, year, maxSpeed, passengers, altitude);
                    vehicles.add(plane);
                } else if (type.equalsIgnoreCase("ship")) {
                    if (tokens.length < 6) {
                        System.err.println("Invalid line: " + line);
                        continue;
                    }

                    String shipType = tokens[5].trim();
                    int displacement = Integer.parseInt(tokens[6].trim());

                    Ship ship = new Ship(brand, model, year, maxSpeed, shipType, displacement);
                    vehicles.add(ship);
                } else {
                    System.err.println("Invalid vehicle type: " + type);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in input file: " + e.getMessage());
            System.exit(1);
        }

        // Establish a database connection

        String url = "jdbc:postgresql://localhost:5432/v6";
        String username = "postgres";
        String password = "postgres";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            System.exit(1);
        }

            // Create a statement object for inserting records into the tables
            PreparedStatement vehicleStmt = conn.prepareStatement("INSERT INTO Vehicle (id, brand, model, year, max_speed) VALUES (?, ?, ?, ?, ?);");
            PreparedStatement planeStmt = conn.prepareStatement("INSERT INTO Plane (vehicle_id, passengers, altitude) VALUES (?, ?, ?);");
            PreparedStatement shipStmt = conn.prepareStatement("INSERT INTO Ship (vehicle_id, ship_type, displacement) VALUES (?, ?, ?);");

            // Insert records into the database
            int x = 1;
            for (Vehicle vehicle : vehicles) {
                // Insert the Vehicle record and retrieve the generated ID
                vehicleStmt.setInt(1, x);
                x++;
                vehicleStmt.setString(2, vehicle.getBrand());
                vehicleStmt.setString(3, vehicle.getModel());
                vehicleStmt.setInt(4, vehicle.getYear());
                vehicleStmt.setInt(5, vehicle.getMaxSpeed());

                boolean res = vehicleStmt.execute();

                // Insert the subtype record (Plane or Ship)
                if (vehicle instanceof Plane) {
                    Plane plane = (Plane) vehicle;
                    planeStmt.setInt(1, x);
                    planeStmt.setInt(2, plane.getPassengers());
                    planeStmt.setInt(3, plane.getAltitude());
                    planeStmt.executeUpdate();
                } else if (vehicle instanceof Ship) {
                    Ship ship = (Ship) vehicle;
                    shipStmt.setInt(1, x);
                    shipStmt.setString(2, ship.getShipType());
                    shipStmt.setInt(3, ship.getDisplacement());
                    shipStmt.executeUpdate();
                }
            }

            // Commit the transaction
            conn.commit();

            // Retrieve all objects from the database
            PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM Vehicle v LEFT JOIN Plane p ON v.id = p.vehicle_id LEFT JOIN Ship s ON v.id = s.vehicle_id");
            ResultSet resultSet = selectStmt.executeQuery();

            List<Vehicle> deserializedVehicles = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                int maxSpeed = resultSet.getInt("max_speed");
                String type = resultSet.getString("type");

                if (type != null && type.equalsIgnoreCase("plane")) {
                    int passengers = resultSet.getInt("passengers");
                    int altitude = resultSet.getInt("altitude");
                    Plane plane = new Plane(brand, model, year, maxSpeed, passengers, altitude);
                    deserializedVehicles.add(plane);
                } else if (type != null && type.equalsIgnoreCase("ship")) {
                    String shipType = resultSet.getString("ship_type");
                    int displacement = resultSet.getInt("displacement");
                    Ship ship = new Ship(brand, model, year, maxSpeed, shipType, displacement);
                    deserializedVehicles.add(ship);
                } else {
                    System.err.println("Unknown vehicle type!");
                }
            }

            // Prepare a statement with user input parameters
            PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM Vehicle WHERE brand LIKE ?");
            String userInput = getUserInput();
            preparedStmt.setString(1, "%" + userInput + "%");
            ResultSet result = preparedStmt.executeQuery();

            // Update one object and delete another object in a transaction with Dirty Read isolation level
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE Vehicle SET year = 2023 WHERE brand = ?");
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM Vehicle WHERE brand = ?");

            updateStmt.setString(1, "Boeing");
            deleteStmt.setString(1, "Hyundai");

            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            try {
                conn.setAutoCommit(false);

                updateStmt.executeUpdate();
                deleteStmt.executeUpdate();

                // Uncomment the following line to test dirty read isolation level
                // Thread.sleep(10000);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Transaction rolled back: " + e.getMessage());
            }

            // Write to the log file
            try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName))) {
                for (Vehicle vehicle : deserializedVehicles) {
                    writer.println("Created vehicle: " + vehicle);
                }
            } catch (IOException e) {
                System.err.println("Error writing log file: " + e.getMessage());
                System.exit(1);
            }

            System.out.println("Program executed successfully!");
    }

    private static String getUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a search string: ");
        String input = null;
        try {
            input = reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading user input: " + e.getMessage());
        }
        return input;
    }
}