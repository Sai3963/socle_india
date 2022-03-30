package net.socle.utils;

import java.util.Random;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 24-03-2022
 */

public class CommonUtil {

    public static String generateOTP(int len) {
        String numbers = "0123456789";
        Random randomMethod = new Random();
        char[] otp = new char[len];
        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt( randomMethod.nextInt( numbers.length() ) );
        }
        return new String( otp );
    }

}
