package com.gabrielmbarboza.openweathermapapp;

import com.gabrielmbarboza.openweathermapapp.util.Util;

/**
 * Created by gabriel on 14/05/17.
 */

public class Weather {
    public double max;
    public double min;
    public String description;
    public String iconUrl;
    public String weekDay;

    public Weather(long timestamp, double max, double min, String description, String icon) {
        this.max = max;
        this.min = min;
        this.weekDay = Util.convertTimestampToDayOfWeek(timestamp);
        this.description = description;
        this.iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

    }
}
