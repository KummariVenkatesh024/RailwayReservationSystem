package main;
import model.Admin;
import model.Passenger;
import model.Ticket;
import model.Train;
import model.User;

import service.BookingService;
import service.CancellationService;
import service.TrainService;
import service.UserService;

import util.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner =new Scanner(System.in);

    private static UserService userService;
    private static TrainService trainService;
    private static BookingService bookingService;
    private static CancellationService cancellationService;

    private static Admin admin =
            new Admin("admin", "admin123");

    public static void main(String[] args) {

        initializeServices();

        System.out.println("==============================================");
        System.out.println("       RAILWAY RESERVATION SYSTEM");
        System.out.println("==============================================");

        mainMenu();
    }

    private static void initializeServices() {

        userService =
                new UserService();

        trainService =
                new TrainService();

        bookingService =
                new BookingService(trainService);

        cancellationService =
                new CancellationService(
                        bookingService,
                        trainService
                );
    }

    private static void mainMenu() {

        while (true) {

            System.out.println("\n========== MAIN MENU ==========");

            System.out.println("1. User Registration");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Search Trains");
            System.out.println("5. Check PNR Status");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");

            int choice =
                    readInt();

            switch (choice) {

                case 1:
                    registerUser();
                    break;

                case 2:
                    userLogin();
                    break;

                case 3:
                    adminLogin();
                    break;

                case 4:
                    searchTrains();
                    break;

                case 5:
                    checkPNR();
                    break;

                case 6:

                    System.out.println(
                            "Thank you for using Railway Reservation System!"
                    );

                    scanner.close();

                    return;

                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    private static void registerUser() {

        System.out.println(
                "\n========== USER REGISTRATION =========="
        );

        System.out.print("Enter username: ");

        String username =
                scanner.nextLine();

        System.out.print("Enter password: ");

        String password =
                scanner.nextLine();

        System.out.print("Enter name: ");

        String name =
                scanner.nextLine();

        System.out.print("Enter phone number: ");

        String phone =
                scanner.nextLine();

        userService.registerUser(
                username,
                password,
                name,
                phone
        );
    }

    private static void userLogin() {

        System.out.println(
                "\n========== USER LOGIN =========="
        );

        System.out.print("Username: ");

        String username =
                scanner.nextLine();

        System.out.print("Password: ");

        String password =
                scanner.nextLine();

        User user =
                userService.login(
                        username,
                        password
                );

        if (user == null) {

            System.out.println(
                    "Invalid username or password."
            );

            return;
        }

        System.out.println(
                "\nLogin successful!"
        );

        System.out.println(
                "Welcome, " + user.getName()
        );

        userMenu(user);
    }

    private static void userMenu(User user) {

        while (true) {

            System.out.println(
                    "\n========== USER MENU =========="
            );

            System.out.println("1. Search Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Check PNR Status");
            System.out.println("5. View My Bookings");
            System.out.println("6. View Profile");
            System.out.println("7. Logout");

            System.out.print("Enter choice: ");

            int choice =
                    readInt();

            switch (choice) {

                case 1:

                    searchTrains();

                    break;

                case 2:

                    bookTicket(user);

                    break;

                case 3:

                    cancelTicket(user);

                    break;

                case 4:

                    checkPNR();

                    break;

                case 5:

                    bookingService
                            .displayUserBookings(
                                    user.getUsername()
                            );

                    break;

                case 6:

                    user.displayUser();

                    break;

                case 7:

                    System.out.println(
                            "Logged out successfully."
                    );

                    return;

                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    private static void searchTrains() {

        System.out.println(
                "\n========== TRAIN SEARCH =========="
        );

        System.out.print("Enter source: ");

        String source =
                scanner.nextLine();

        System.out.print("Enter destination: ");

        String destination =
                scanner.nextLine();

        trainService.searchTrains(
                source,
                destination
        );
    }

    private static void bookTicket(User user) {

        System.out.println(
                "\n========== BOOK TICKET =========="
        );

        trainService.displayAllTrains();

        System.out.print(
                "Enter train number: "
        );

        int trainNumber =
                readInt();

        Train train =
                trainService.findTrain(
                        trainNumber
                );

        if (train == null) {

            System.out.println(
                    "Train not found."
            );

            return;
        }

        System.out.println(
                "Available Seats: "
                        + train.getAvailableSeats()
        );

        System.out.print(
                "Enter number of passengers: "
        );

        int passengerCount =
                readInt();

        if (!InputValidator.isValidSeats(
                passengerCount)) {

            System.out.println(
                    "Invalid passenger count."
            );

            return;
        }

        if (passengerCount
                > train.getAvailableSeats()) {

            System.out.println(
                    "Not enough seats available."
            );

            return;
        }

        List<Passenger> passengers =
                new ArrayList<>();

        for (int i = 1;
             i <= passengerCount;
             i++) {

            System.out.println(
                    "\nPassenger " + i
            );

            System.out.print("Name: ");

            String name =
                    scanner.nextLine();

            System.out.print("Age: ");

            int age =
                    readInt();

            System.out.print(
                    "Gender (Male/Female/Other): "
            );

            String gender =
                    scanner.nextLine();

            if (!InputValidator.isValidName(name)
                    || !InputValidator.isValidAge(age)
                    || !InputValidator.isValidGender(gender)) {

                System.out.println(
                        "Invalid passenger details."
                );

                return;
            }

            Passenger passenger =
                    new Passenger(
                            name,
                            age,
                            gender
                    );

            passengers.add(passenger);
        }

        System.out.println(
                "\nProcessing booking..."
        );

        bookingService.bookTicketUsingThread(
                user.getUsername(),
                trainNumber,
                passengers
        );
    }

    private static void cancelTicket(User user) {

        System.out.println(
                "\n========== CANCEL TICKET =========="
        );

        System.out.print("Enter PNR: ");

        String pnr =
                scanner.nextLine();

        cancellationService.cancelTicket(
                pnr,
                user.getUsername()
        );
    }

    private static void checkPNR() {

        System.out.println(
                "\n========== PNR STATUS =========="
        );

        System.out.print("Enter PNR: ");

        String pnr =
                scanner.nextLine();

        bookingService.checkPNR(pnr);
    }

    private static void adminLogin() {

        System.out.println(
                "\n========== ADMIN LOGIN =========="
        );

        System.out.print("Username: ");

        String username =
                scanner.nextLine();

        System.out.print("Password: ");

        String password =
                scanner.nextLine();

        if (admin.login(username, password)) {

            System.out.println(
                    "Admin login successful."
            );

            adminMenu();

        } else {

            System.out.println(
                    "Invalid admin credentials."
            );
        }
    }

    private static void adminMenu() {

        while (true) {

            System.out.println(
                    "\n========== ADMIN MENU =========="
            );

            System.out.println("1. View All Trains");
            System.out.println("2. Add Train");
            System.out.println("3. Remove Train");
            System.out.println("4. View All Users");
            System.out.println("5. View All Bookings");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");

            int choice =
                    readInt();

            switch (choice) {

                case 1:

                    trainService.displayAllTrains();

                    break;

                case 2:

                    addTrain();

                    break;

                case 3:

                    removeTrain();

                    break;

                case 4:

                    userService.displayAllUsers();

                    break;

                case 5:

                    displayAllBookings();

                    break;

                case 6:

                    System.out.println(
                            "Admin logged out."
                    );

                    return;

                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    private static void addTrain() {

        System.out.println(
                "\n========== ADD TRAIN =========="
        );

        System.out.print("Train number: ");

        int trainNumber =
                readInt();

        System.out.print("Train name: ");

        String trainName =
                scanner.nextLine();

        System.out.print("Source: ");

        String source =
                scanner.nextLine();

        System.out.print("Destination: ");

        String destination =
                scanner.nextLine();

        System.out.print("Total seats: ");

        int totalSeats =
                readInt();

        trainService.addTrain(
                trainNumber,
                trainName,
                source,
                destination,
                totalSeats
        );
    }

    private static void removeTrain() {

        System.out.println(
                "\n========== REMOVE TRAIN =========="
        );

        System.out.print(
                "Enter train number: "
        );

        int trainNumber =
                readInt();

        trainService.removeTrain(
                trainNumber
        );
    }

    private static void displayAllBookings() {

        List<Ticket> bookings =
                bookingService.getBookings();

        if (bookings.isEmpty()) {

            System.out.println(
                    "No bookings available."
            );

            return;
        }

        System.out.println(
                "\n========== ALL BOOKINGS =========="
        );

        for (Ticket ticket : bookings) {

            ticket.displayTicket();
        }
    }

    private static int readInt() {

        while (true) {

            try {

                String input =
                        scanner.nextLine();

                return Integer.parseInt(
                        input.trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                        "Please enter a valid number: "
                );
            }
        }
    }
}