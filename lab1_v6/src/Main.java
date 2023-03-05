import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Main input_file.csv");
            System.exit(1);
        }

        String inputFileName = args[0];
        String outputFileName = "output.bin";
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

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputFileName))) {
            for (Vehicle vehicle : vehicles) {
                outputStream.writeObject(vehicle);
            }
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
            System.exit(1);
        }

        List<Vehicle> deserializedVehicles = new ArrayList<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(outputFileName))) {
            while (true) {
                try {
                    Vehicle vehicle = (Vehicle) inputStream.readObject();
                    deserializedVehicles.add(vehicle);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading output file: " + e.getMessage());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found while deserializing: " + e.getMessage());
            System.exit(1);
        }

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
}