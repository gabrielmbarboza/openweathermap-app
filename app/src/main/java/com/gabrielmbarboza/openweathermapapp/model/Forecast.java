package com.gabrielmbarboza.openweathermapapp.model;

/**
 * Created by gmoraes on 24/05/17.
 */

public class Forecast {
    private int _id;
    private int weatherDate;
    private int day;
    private Double min;
    private Double max;
    private Double night;
    private Double even;
    private Double morn;
    private Double pressure;
    private int humidity;
    private int weatherId;
    private String main;
    private String description;
    private Double speed;
    private int deg;
    private int clouds;
    private Double rain;
    private String icon;
    private City city;

    public Forecast() {
    }

    public Forecast(int _id, int weatherDate, int day, Double min, Double max, Double night, Double even,
                    Double morn, Double pressure, int humidity, int weatherId,
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
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public int getID() {
        return _id;
    }

    public int getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(int weatherDate) {
        this.weatherDate = weatherDate;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public Double getEven() {
        return even;
    }

    public void setEven(Double even) {
        this.even = even;
    }

    public Double getMorn() {
        return morn;
    }

    public void setMorn(Double morn) {
        this.morn = morn;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weather_id) {
        this.weatherId = weather_id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
