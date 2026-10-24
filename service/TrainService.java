package service;

import model.Train;
import util.FileManager;
import util.InputValidator;

import java.util.List;

public class TrainService {

    private List<Train> trains;

    public TrainService() {

        trains = FileManager.loadList(
                FileManager.TRAINS_FILE
        );

        if (trains.isEmpty()) {

            addDefaultTrains();
        }
    }

    private void addDefaultTrains() {

        trains.add(
                new Train(
                        12760,
                        "Charminar Express",
                        "Hyderabad",
                        "Chennai",
                        100
                )
        );

        trains.add(
                new Train(
                        17487,
                        "Tirumala Express",
                        "Visakhapatnam",
                        "Tirupati",
                        120
                )
        );

        trains.add(
                new Train(
                        12863,
                        "HWH SMVB Express",
                        "Howrah",
                        "Bengaluru",
                        150
                )
        );

        trains.add(
                new Train(
                        12603,
                        "Hyderabad Express",
                        "Chennai",
                        "Hyderabad",
                        100
                )
        );

        saveTrains();
    }

    public void displayAllTrains() {

        if (trains.isEmpty()) {

            System.out.println("No trains available.");

            return;
        }

        System.out.println("\n========== ALL TRAINS ==========");

        for (Train train : trains) {

            train.displayTrain();
        }
    }

    public void searchTrains(String source,
                             String destination) {

        boolean found = false;

        System.out.println(
                "\n========== SEARCH RESULTS =========="
        );

        for (Train train : trains) {

            if (train.getSource()
                    .equalsIgnoreCase(source)
                    &&
                train.getDestination()
                    .equalsIgnoreCase(destination)) {

                train.displayTrain();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No trains found for this route."
            );
        }
    }

    public Train findTrain(int trainNumber) {

        for (Train train : trains) {

            if (train.getTrainNumber() == trainNumber) {

                return train;
            }
        }

        return null;
    }

    public boolean addTrain(int trainNumber,
                            String trainName,
                            String source,
                            String destination,
                            int totalSeats) {

        if (!InputValidator.isValidSeats(totalSeats)) {

            return false;
        }

        if (findTrain(trainNumber) != null) {

            System.out.println(
                    "Train number already exists."
            );

            return false;
        }

        Train train =
                new Train(
                        trainNumber,
                        trainName,
                        source,
                        destination,
                        totalSeats
                );

        trains.add(train);

        saveTrains();

        System.out.println(
                "Train added successfully."
        );

        return true;
    }

    public boolean removeTrain(int trainNumber) {

        Train train = findTrain(trainNumber);

        if (train == null) {

            System.out.println(
                    "Train not found."
            );

            return false;
        }

        trains.remove(train);

        saveTrains();

        System.out.println(
                "Train removed successfully."
        );

        return true;
    }

    public void saveTrains() {

        FileManager.saveList(
                FileManager.TRAINS_FILE,
                trains
        );
    }
}