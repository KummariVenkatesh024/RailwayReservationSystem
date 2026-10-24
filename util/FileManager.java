package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static final String USERS_FILE =
            "data/users.dat";

    public static final String TRAINS_FILE =
            "data/trains.dat";

    public static final String BOOKINGS_FILE =
            "data/bookings.dat";


    // ================= SAVE DATA =================

    public static void saveData(
            String fileName,
            Object data) {

        try {

            File file = new File(fileName);

            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(file)
                    );

            output.writeObject(data);

            output.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving data: "
                            + e.getMessage()
            );
        }
    }


    // ================= LOAD DATA =================

    public static Object loadData(
            String fileName) {

        File file = new File(fileName);

        // First-time application startup.
        // File does not exist yet, so return null
        // without showing an error.
        if (!file.exists()) {
            return null;
        }

        try {

            ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream(file)
                    );

            Object data = input.readObject();

            input.close();

            return data;

        } catch (IOException
                 | ClassNotFoundException e) {

            System.out.println(
                    "Error loading data from "
                            + fileName
                            + ": "
                            + e.getMessage()
            );

            return null;
        }
    }


    // ================= LOAD LIST =================

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadList(
            String fileName) {

        Object data = loadData(fileName);

        if (data instanceof List<?>) {

            return (List<T>) data;
        }

        return new ArrayList<>();
    }


    // ================= SAVE LIST =================

    public static void saveList(
            String fileName,
            List<?> list) {

        saveData(fileName, list);
    }
}