package com.gabrielmbarboza.openweathermapapp.db.model;

/**
 * Created by gmoraes on 24/05/17.
 */

public class City {
    public long _id;
    public String cityId;
    public String name;
    public String lon;
    public String lat;
    public String country;
    public Double population;

    public City() {
    }

    public City(long _id, String cityId, String name, String lon, String lat, String country, Double population) {
        this._id = _id;
        this.cityId = cityId;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.country = country;
        this.population = population;
    }

    public City(String cityId, String name, String lon, String lat, String country, Double population) {
        this._id = _id;
        this.cityId = cityId;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.country = country;
        this.population = population;
    }
}
