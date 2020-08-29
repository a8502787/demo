package com.gcb.vehiclemanagement.util;

import java.util.Date;
import java.util.Random;

public class RandomUtil {

    public static String getRandom() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 4; i++) {
            result += random.nextInt(10);
        }
        String currentTime = Long.toString(new Date().getTime());
        return result + currentTime.substring(currentTime.length() - 6);
    }
}
