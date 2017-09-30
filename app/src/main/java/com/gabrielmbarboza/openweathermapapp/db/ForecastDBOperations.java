package com.gabrielmbarboza.openweathermapapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gabrielmbarboza.openweathermapapp.db.model.City;
import com.gabrielmbarboza.openweathermapapp.db.model.Forecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmoraes on 24/05/17.
 */

public class ForecastDBOperations {
    private SQLiteOpenHelper helper;
    private String[] projections = {
            ForecastContract.ForecastEntry._ID,
            ForecastContract.ForecastEntry.COLUMN_NAME_WEATHER_ID,
            ForecastContract.ForecastEntry.COLUMN_NAME_DT,
            ForecastContract.ForecastEntry.COLUMN_NAME_DESCRIPTION,
            ForecastContract.ForecastEntry.COLUMN_NAME_MIN,
            ForecastContract.ForecastEntry.COLUMN_NAME_MAX,
            ForecastContract.ForecastEntry.COLUMN_NAME_CLOUDS,
            ForecastContract.ForecastEntry.COLUMN_NAME_DAY,
            ForecastContract.ForecastEntry.COLUMN_NAME_EVEN,
            ForecastContract.ForecastEntry.COLUMN_NAME_NIGHT,
            ForecastContract.ForecastEntry.COLUMN_NAME_DEG,
            ForecastContract.ForecastEntry.COLUMN_NAME_HUMIDITY,
            ForecastContract.ForecastEntry.COLUMN_NAME_SPEED,
            ForecastContract.ForecastEntry.COLUMN_NAME_MAIN,
            ForecastContract.ForecastEntry.COLUMN_NAME_MORN,
            ForecastContract.ForecastEntry.COLUMN_NAME_PRESSURE,
            ForecastContract.ForecastEntry.COLUMN_NAME_RAIN,
            ForecastContract.ForecastEntry.COLUMN_NAME_CITY_ID,
            ForecastContract.ForecastEntry.COLUMN_NAME_ICON
    };

    private String[] mainProjections = {
            ForecastContract.ForecastEntry._ID,
            ForecastContract.ForecastEntry.COLUMN_NAME_CITY_ID,
            ForecastContract.ForecastEntry.COLUMN_NAME_DESCRIPTION,
            ForecastContract.ForecastEntry.COLUMN_NAME_MIN,
            ForecastContract.ForecastEntry.COLUMN_NAME_MAX,
            ForecastContract.ForecastEntry.COLUMN_NAME_ICON,
    };

    private String selection = ForecastContract.ForecastEntry.COLUMN_NAME_CITY_ID + " = ?";

    public ForecastDBOperations(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void addForecast(Forecast Forecast) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_WEATHER_ID, Forecast.weatherId);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_DT, Forecast.weatherDate);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_DESCRIPTION, Forecast.description);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_MIN, Forecast.min);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_MAX, Forecast.max);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_CLOUDS, Forecast.clouds);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_DAY, Forecast.day);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_EVEN, Forecast.even);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_NIGHT, Forecast.night);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_DEG, Forecast.deg);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_HUMIDITY, Forecast.humidity);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_SPEED, Forecast.speed);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_MAIN, Forecast.main);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_MORN, Forecast.morn);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_PRESSURE, Forecast.pressure);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_RAIN, Forecast.rain);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_CITY_ID, Forecast.city.cityId);
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_ICON, Forecast.icon);

        db.insert(ForecastContract.ForecastEntry.TABLE_NAME, null, values);

        db.close();
    }

    public void addAll(List<Forecast> forecasts) {
        if(forecasts != null && !forecasts.isEmpty()) {
            for (Forecast forecast : forecasts) {
                addForecast(forecast);
            }
        }
    }

    public Forecast getForecast(String ForecastId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                ForecastContract.ForecastEntry.TABLE_NAME,
                projections,
                selection,
                new String[]{ForecastId},
                null,
                null,
                null
        );

        cursor.moveToFirst();

        Forecast Forecast = toForecast(cursor);

        cursor.close();

        return Forecast;
    }

    public List<Forecast> getAllForecasts() {
        List<Forecast> cities = new ArrayList<Forecast>();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                ForecastContract.ForecastEntry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Forecast Forecast = toForecast(cursor);
            cities.add(Forecast);
        }

        cursor.close();

        return cities;
    }

    public int getCount() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(ForecastContract.ForecastEntry.SQL_COUNT_FORECASTS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(cursor.getColumnIndexOrThrow(ForecastContract.ForecastEntry.COLUMN_NAME_COUNT));
        cursor.close();

        return count;
    }

    private Forecast toForecast(Cursor cursor) {
        int _id = cursor.getInt(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry._ID
        ));

        String weatherId = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_WEATHER_ID
        ));


        int weatherDate = cursor.getInt(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_DT
        ));

        String description = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_DESCRIPTION
        ));

        Double min = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_MIN
        ));

        Double max = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_MAX
        ));

        int clouds = cursor.getInt(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_CLOUDS
        ));

        int day = cursor.getInt(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_DAY
        ));

        Double even = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_EVEN
        ));

        Double night = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_NIGHT
        ));

        int deg = cursor.getInt(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_DEG
        ));

        int humidity = cursor.getInt(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_HUMIDITY
        ));

        Double speed = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_SPEED
        ));

        String main = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_MAIN
        ));

        Double morn = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_MORN
        ));

        Double pressure = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_PRESSURE
        ));

        Double rain = cursor.getDouble(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_RAIN
        ));

        String cityId = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_CITY_ID
        ));

        String icon = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_ICON
        ));

        CityDBOperations cityOp = new CityDBOperations(helper);
        City city = cityOp.getCity(cityId);

        Forecast Forecast = new Forecast(_id, weatherDate, day, min, max, night, even,
                morn, pressure, humidity, weatherId, main, description, speed, deg, clouds,
                rain, icon, city
        );

        return Forecast;
    }
}
