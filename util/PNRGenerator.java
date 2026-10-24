package util;

import java.util.Random;

public class PNRGenerator {

    private static final Random random =
            new Random();

    public static String generatePNR() {

        int number =
                100000000
                + random.nextInt(900000000);

        return String.valueOf(number);
    }
}