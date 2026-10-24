package service;

import model.User;
import util.FileManager;
import util.InputValidator;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {

        users = FileManager.loadList(
                FileManager.USERS_FILE
        );
    }

    public boolean registerUser(String username,
                                String password,
                                String name,
                                String phone) {

        if (!InputValidator.isValidUsername(username)) {

            System.out.println(
                    "Username must contain at least 3 characters."
            );

            return false;
        }

        if (!InputValidator.isValidPassword(password)) {

            System.out.println(
                    "Password must contain at least 4 characters."
            );

            return false;
        }

        if (!InputValidator.isValidName(name)) {

            System.out.println("Invalid name.");

            return false;
        }

        if (!InputValidator.isValidPhone(phone)) {

            System.out.println(
                    "Phone number must contain exactly 10 digits."
            );

            return false;
        }

        if (findUser(username) != null) {

            System.out.println(
                    "Username already exists."
            );

            return false;
        }

        User user =
                new User(
                        username,
                        password,
                        name,
                        phone
                );

        users.add(user);

        saveUsers();

        System.out.println(
                "Registration successful."
        );

        return true;
    }

    public User login(String username,
                      String password) {

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                return user;
            }
        }

        return null;
    }

    public User findUser(String username) {

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }

    public void displayAllUsers() {

        if (users.isEmpty()) {

            System.out.println("No users registered.");

            return;
        }

        System.out.println("\n========== USERS ==========");

        for (User user : users) {

            user.displayUser();
            System.out.println("----------------------------");
        }
    }

    private void saveUsers() {
     FileManager.saveList(FileManager.USERS_FILE, users);
    }
}