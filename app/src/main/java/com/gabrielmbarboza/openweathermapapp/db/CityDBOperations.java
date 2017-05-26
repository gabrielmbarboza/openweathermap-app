package com.gabrielmbarboza.openweathermapapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gabrielmbarboza.openweathermapapp.db.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmoraes on 24/05/17.
 */

public class CityDBOperations {
    private SQLiteOpenHelper helper;
    private String[] projections = {
            CityContract.CityEntry._ID,
            CityContract.CityEntry.COLUMN_NAME_NAME,
            CityContract.CityEntry.COLUMN_NAME_CITY_ID,
            CityContract.CityEntry.COLUMN_NAME_COUNTRY,
            CityContract.CityEntry.COLUMN_NAME_COUNTRY,
            CityContract.CityEntry.COLUMN_NAME_LAT,
            CityContract.CityEntry.COLUMN_NAME_LON,
            CityContract.CityEntry.COLUMN_NAME_POPULATION
    };

    private String selection = CityContract.CityEntry.COLUMN_NAME_CITY_ID + " = ?";

    public CityDBOperations(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void addCity(City city) {
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(CityContract.CityEntry.COLUMN_NAME_NAME, city.getName());
            values.put(CityContract.CityEntry.COLUMN_NAME_CITY_ID, city.getCityId());
            values.put(CityContract.CityEntry.COLUMN_NAME_COUNTRY, city.getCountry());
            values.put(CityContract.CityEntry.COLUMN_NAME_LAT, city.getLat());
            values.put(CityContract.CityEntry.COLUMN_NAME_LON, city.getLon());
            values.put(CityContract.CityEntry.COLUMN_NAME_POPULATION, city.getPopulation());

            db.insert(CityContract.CityEntry.TABLE_NAME, null, values);

            db.close();
    }

    public City getCity(String cityId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                CityContract.CityEntry.TABLE_NAME,
                projections,
                selection,
                new String[]{cityId},
                null,
                null,
                null
        );

        cursor.moveToFirst();

        City city = createCity(cursor);

        cursor.close();

        return city;
    }

    //TODO: Refatorar
    public boolean isCityExist(String cityId) {
        City city = getCity(cityId);

        return (city.getCityId() != null);
    }

    public List<City> getAllCities() {
        List<City> cities = new ArrayList<City>();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                CityContract.CityEntry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            City city = createCity(cursor);
            cities.add(city);
        }

        cursor.close();

        return cities;
    }

    public int getCount() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(CityContract.CityEntry.SQL_COUNT_CITIES, null);
        cursor.moveToFirst();
        cursor.close();

        return cursor.getCount();
    }

    private City createCity(Cursor cursor) {
        long _id = cursor.getLong(cursor.getColumnIndexOrThrow(
                CityContract.CityEntry._ID
        ));

        String name = cursor.getString(cursor.getColumnIndexOrThrow(
                CityContract.CityEntry.COLUMN_NAME_NAME
        ));

        String cityId = cursor.getString(cursor.getColumnIndexOrThrow(
                CityContract.CityEntry.COLUMN_NAME_CITY_ID
        ));

        String country = cursor.getString(cursor.getColumnIndexOrThrow(
                CityContract.CityEntry.COLUMN_NAME_COUNTRY
        ));

        String lat = cursor.getString(cursor.getColumnIndexOrThrow(
                CityContract.CityEntry.COLUMN_NAME_LAT
        ));

        String lon = cursor.getString(cursor.getColumnIndexOrThrow(
                CityContract.CityEntry.COLUMN_NAME_LON
        ));

        Double population = cursor.getDouble(cursor.getColumnIndexOrThrow(
                CityContract.CityEntry.COLUMN_NAME_POPULATION
        ));

        City city = new City(_id,cityId,name,lon,lat,country,population);

        return city;
    }
}
