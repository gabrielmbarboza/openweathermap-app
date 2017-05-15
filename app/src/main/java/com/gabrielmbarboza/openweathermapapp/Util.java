package com.gabrielmbarboza.openweathermapapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by gabriel on 14/05/17.
 */

public class Util {
    public static String convertTimestampToDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        TimeZone timeZone = TimeZone.getDefault();
        cal.add(Calendar.MILLISECOND, timeZone.getOffset(cal.getTimeInMillis()));
        SimpleDateFormat df = new SimpleDateFormat("EEEE");

        return df.format(cal.getTime());
    }
}
