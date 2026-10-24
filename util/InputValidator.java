package util;

public class InputValidator {

    public static boolean isValidUsername(
            String username) {

        return username != null
                && username.trim().length() >= 3;
    }

    public static boolean isValidPassword(
            String password) {

        return password != null
                && password.length() >= 4;
    }

    public static boolean isValidName(
            String name) {

        return name != null
                && name.trim().length() >= 2;
    }

    public static boolean isValidPhone(
            String phone) {

        return phone != null
                && phone.matches("\\d{10}");
    }

    public static boolean isValidAge(
            int age) {

        return age >= 1 && age <= 120;
    }

    public static boolean isValidGender(
            String gender) {

        if (gender == null) {
            return false;
        }

        return gender.equalsIgnoreCase("Male")
                || gender.equalsIgnoreCase("Female")
                || gender.equalsIgnoreCase("Other");
    }

    public static boolean isValidSeats(
            int seats) {

        return seats > 0;
    }
}