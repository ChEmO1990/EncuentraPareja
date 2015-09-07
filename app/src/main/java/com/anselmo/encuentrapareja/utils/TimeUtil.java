package com.anselmo.encuentrapareja.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by naranya on 9/7/15.
 */
public class TimeUtil {

    /**
     * Gets the current time
     *
     * @return String with the current time and date
     */
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
