package com.gcb.vehiclemanagement.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateToStringConverter {

    public static String getStringDate(Timestamp date) {
        if (date == null || date.equals("")) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }
}
