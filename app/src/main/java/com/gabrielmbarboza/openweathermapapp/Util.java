package com.gabrielmbarboza.openweathermapapp;

import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by gabriel on 14/05/17.
 */

public class Util {
    public static String convertTimestampToDayOfWeek(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        TimeZone timeZone = TimeZone.getDefault();
        cal.add(Calendar.MILLISECOND, timeZone.getOffset(cal.getTimeInMillis()));
        SimpleDateFormat df = new SimpleDateFormat("EEEE");

        return df.format(cal.getTime());
    }
}
