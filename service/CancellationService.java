package service;

import model.Ticket;
import model.Train;

public class CancellationService {

    private BookingService bookingService;
    private TrainService trainService;

    public CancellationService(
            BookingService bookingService,
            TrainService trainService) {

        this.bookingService = bookingService;
        this.trainService = trainService;
    }

    public boolean cancelTicket(String pnr,
                                String username) {

        Ticket ticket =
                bookingService.findTicket(pnr);

        if (ticket == null) {

            System.out.println(
                    "Ticket not found."
            );

            return false;
        }

        if (!ticket.getUsername()
                .equalsIgnoreCase(username)) {

            System.out.println(
                    "You can cancel only your own ticket."
            );

            return false;
        }

        if (ticket.getStatus()
                .equalsIgnoreCase("CANCELLED")) {

            System.out.println(
                    "Ticket is already cancelled."
            );

            return false;
        }

        Train train =
                trainService.findTrain(
                        ticket.getTrainNumber()
                );

        if (train != null) {

            int passengerCount =
                    ticket.getPassengers().size();

            train.cancelSeat(passengerCount);
        }

        ticket.cancelTicket();

        bookingService.saveBookings();

        trainService.saveTrains();

        System.out.println(
                "Ticket cancelled successfully."
        );

        return true;
    }
}