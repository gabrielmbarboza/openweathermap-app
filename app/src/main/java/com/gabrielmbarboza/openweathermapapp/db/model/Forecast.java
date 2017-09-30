package com.gabrielmbarboza.openweathermapapp.db.model;

import com.gabrielmbarboza.openweathermapapp.util.Util;

/**
 * Created by gmoraes on 24/05/17.
 */

public class Forecast {
    public int _id;
    public int weatherDate;
    public int day;
    public Double min;
    public Double max;
    public Double night;
    public Double even;
    public Double morn;
    public Double pressure;
    public int humidity;
    public String weatherId;
    public String main;
    public String description;
    public Double speed;
    public int deg;
    public int clouds;
    public Double rain;
    public String icon;
    public City city;
    public String iconUrl;
    public String weekDay;

    public Forecast() {
    }

    public Forecast(int _id, int weatherDate, int day, Double min, Double max, Double night, Double even,
                    Double morn, Double pressure, int humidity, String weatherId,
                    String main, String description, Double speed, int deg, int clouds,
                    Double rain, String icon, City city) {
        this._id = _id;
        this.weatherDate = weatherDate;
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.even = even;
        this.morn = morn;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherId = weatherId;
        this.main = main;
        this.description = description;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
        this.icon = icon;
        this.city = city;

        this.weekDay = Util.convertTimestampToDayOfWeek(weatherDate);
        this.description = description;
        this.iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
    }

    public Forecast(int weatherDate, int day, Double min, Double max, Double night, Double even,
                    Double morn, Double pressure, int humidity, String weatherId,
                    String main, String description, Double speed, int deg, int clouds,
                    Double rain, String icon, City city) {
        this.weatherDate = weatherDate;
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.even = even;
        this.morn = morn;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherId = weatherId;
        this.main = main;
        this.description = description;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
        this.icon = icon;
        this.city = city;

        this.weekDay = Util.convertTimestampToDayOfWeek(weatherDate);
        this.description = description;
        this.iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
    }
}
