package model;

import java.io.Serializable;
import java.util.List;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pnr;
    private String username;
    private int trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private List<Passenger> passengers;
    private String status;

    public Ticket(String pnr,
                  String username,
                  int trainNumber,
                  String trainName,
                  String source,
                  String destination,
                  List<Passenger> passengers) {

        this.pnr = pnr;
        this.username = username;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.passengers = passengers;
        this.status = "CONFIRMED";
    }

    public String getPnr() {
        return pnr;
    }

    public String getUsername() {
        return username;
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

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public String getStatus() {
        return status;
    }

    public void cancelTicket() {
        status = "CANCELLED";
    }

    public void displayTicket() {

        System.out.println("\n========================================");
        System.out.println("              TICKET");
        System.out.println("========================================");

        System.out.println("PNR          : " + pnr);
        System.out.println("Username     : " + username);
        System.out.println("Train Number : " + trainNumber);
        System.out.println("Train Name   : " + trainName);
        System.out.println("Route        : " + source + " -> " + destination);
        System.out.println("Status       : " + status);

        System.out.println("\nPassengers:");

        for (int i = 0; i < passengers.size(); i++) {

            System.out.println(
                    (i + 1) + ". " +
                    passengers.get(i)
            );
        }

        System.out.println("========================================");
    }

    @Override
    public String toString() {

        return "PNR: " + pnr +
                " | User: " + username +
                " | Train: " + trainNumber +
                " | Status: " + status;
    }
}