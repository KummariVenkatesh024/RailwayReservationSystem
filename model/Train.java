package model;

import java.io.Serializable;

public class Train implements Serializable {

    private static final long serialVersionUID = 1L;

    private int trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private int totalSeats;
    private int availableSeats;

    public Train(int trainNumber, String trainName,
                 String source, String destination,
                 int totalSeats) {

        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean bookSeat(int numberOfSeats) {

        if (numberOfSeats <= 0) {
            return false;
        }

        if (availableSeats >= numberOfSeats) {
            availableSeats -= numberOfSeats;
            return true;
        }

        return false;
    }

    public void cancelSeat(int numberOfSeats) {

        availableSeats += numberOfSeats;

        if (availableSeats > totalSeats) {
            availableSeats = totalSeats;
        }
    }

    public void displayTrain() {

        System.out.println("----------------------------------------");
        System.out.println("Train Number   : " + trainNumber);
        System.out.println("Train Name     : " + trainName);
        System.out.println("Source         : " + source);
        System.out.println("Destination    : " + destination);
        System.out.println("Total Seats    : " + totalSeats);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("----------------------------------------");
    }

    @Override
    public String toString() {

        return trainNumber + " | " +
                trainName + " | " +
                source + " -> " +
                destination + " | Seats: " +
                availableSeats + "/" + totalSeats;
    }
}