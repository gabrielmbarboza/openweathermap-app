package com.gabrielmbarboza.openweathermapapp.db.model;

/**
 * Created by gmoraes on 24/05/17.
 */

public class City {
    private long _id;
    private String cityId;
    private String name;
    private String lon;
    private String lat;
    private String country;
    private Double population;

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

    public long getID() {
        return _id;
    }

    public void setID(long _id) {
        this._id = _id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }
}
