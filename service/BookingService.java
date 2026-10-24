package service;

import model.Passenger;
import model.Ticket;
import model.Train;
import util.FileManager;
import util.InputValidator;
import util.PNRGenerator;

import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private List<Ticket> bookings;

    private TrainService trainService;

    public BookingService(TrainService trainService) {

        this.trainService = trainService;

        bookings = FileManager.loadList(
                FileManager.BOOKINGS_FILE
        );
    }

    public synchronized Ticket bookTicket(
            String username,
            int trainNumber,
            List<Passenger> passengers) {

        Train train =
                trainService.findTrain(trainNumber);

        if (train == null) {

            System.out.println(
                    "Train not found."
            );

            return null;
        }

        if (passengers == null
                || passengers.isEmpty()) {

            System.out.println(
                    "Passenger list cannot be empty."
            );

            return null;
        }

        int passengerCount = passengers.size();

        if (train.getAvailableSeats()
                < passengerCount) {

            System.out.println(
                    "Not enough seats available."
            );

            return null;
        }

        boolean booked =
                train.bookSeat(passengerCount);

        if (!booked) {

            System.out.println(
                    "Unable to book seats."
            );

            return null;
        }

        String pnr =
                PNRGenerator.generatePNR();

        Ticket ticket =
                new Ticket(
                        pnr,
                        username,
                        train.getTrainNumber(),
                        train.getTrainName(),
                        train.getSource(),
                        train.getDestination(),
                        passengers
                );

        bookings.add(ticket);

        saveBookings();

        trainService.saveTrains();

        System.out.println(
                "\nTicket booked successfully!"
        );

        System.out.println(
                "Generated PNR: " + pnr
        );

        return ticket;
    }

    public void bookTicketUsingThread(
            String username,
            int trainNumber,
            List<Passenger> passengers) {

        Thread bookingThread =
                new Thread(() -> {

                    bookTicket(
                            username,
                            trainNumber,
                            passengers
                    );
                });

        bookingThread.start();

        try {

            bookingThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Booking thread interrupted."
            );
        }
    }

    public Ticket findTicket(String pnr) {

        for (Ticket ticket : bookings) {

            if (ticket.getPnr().equalsIgnoreCase(pnr)) {

                return ticket;
            }
        }

        return null;
    }

    public void checkPNR(String pnr) {

        Ticket ticket = findTicket(pnr);

        if (ticket == null) {

            System.out.println(
                    "PNR not found."
            );

            return;
        }

        ticket.displayTicket();
    }

    public void displayUserBookings(String username) {

        boolean found = false;

        System.out.println(
                "\n========== YOUR BOOKINGS =========="
        );

        for (Ticket ticket : bookings) {

            if (ticket.getUsername()
                    .equalsIgnoreCase(username)) {

                ticket.displayTicket();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No bookings found."
            );
        }
    }

    public List<Ticket> getBookings() {

        return bookings;
    }

    public void saveBookings() {

        FileManager.saveList(
                FileManager.BOOKINGS_FILE,
                bookings
        );
    }
}