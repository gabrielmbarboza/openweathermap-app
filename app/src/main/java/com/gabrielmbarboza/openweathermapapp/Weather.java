package com.gabrielmbarboza.openweathermapapp;

/**
 * Created by gabriel on 14/05/17.
 */

public class Weather {
    double max;
    double min;
    String description;
    String iconUrlPath;
    String weekDay;

    public Weather(long timestamp, double max, double min, String description, String icon) {
        this.max = max;
        this.min = min;
        this.weekDay = Util.convertTimestampToDayOfWeek(timestamp);
        this.description = description;
        this.iconUrlPath = "http://openweathermap.org/img/w/" + icon + ".png";

    }
}
