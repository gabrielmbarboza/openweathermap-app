package com.gabrielmbarboza.openweathermapapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gabrielmbarboza.openweathermapapp.model.Forecast;

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

        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_WEATHER_ID, Forecast.getWeatherId());
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_DT, Forecast.getWeatherDate());
        values.put(ForecastContract.ForecastEntry.COLUMN_NAME_DESCRIPTION, Forecast.getDescription());

        db.insert(ForecastContract.ForecastEntry.TABLE_NAME, null, values);

        db.close();
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

        Forecast Forecast = createForecast(cursor);

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
            Forecast Forecast = createForecast(cursor);
            cities.add(Forecast);
        }

        cursor.close();

        return cities;
    }

    public int getCount() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(ForecastContract.ForecastEntry.SQL_COUNT_FORECASTS, null);
        cursor.moveToFirst();
        cursor.close();

        return cursor.getCount();
    }

    private Forecast createForecast(Cursor cursor) {
        long _id = cursor.getLong(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry._ID
        ));

        /*String name = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_NAME
        ));

        String ForecastId = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_Forecast_ID
        ));

        String country = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_COUNTRY
        ));

        String lat = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_LAT
        ));

        String lon = cursor.getString(cursor.getColumnIndexOrThrow(
                ForecastContract.ForecastEntry.COLUMN_NAME_LON
        ));

        Double population = cursor.getDouble(cursor.getColumnIndexOrThrow(
               ForecastContract.ForecastEntry.COLUMN_NAME_POPULATION
        ));*/

        Forecast Forecast = new Forecast();

        return Forecast;
    }
}
