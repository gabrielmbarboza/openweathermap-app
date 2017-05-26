package com.gabrielmbarboza.openweathermapapp.db;

import android.provider.BaseColumns;

/**
 * Created by gmoraes on 23/05/17.
 */

public class ForecastContract {
    private ForecastContract() {}

    public static class ForecastEntry implements BaseColumns {
        public static final String TABLE_NAME = "forecasts";
        public static final String COLUMN_NAME_DT = "dt";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_MIN = "min";
        public static final String COLUMN_NAME_MAX = "max";
        public static final String COLUMN_NAME_NIGHT = "night";
        public static final String COLUMN_NAME_EVEN = "even";
        public static final String COLUMN_NAME_MORN = "morn";
        public static final String COLUMN_NAME_PRESSURE = "pressure";
        public static final String COLUMN_NAME_HUMIDITY = "humidity";
        public static final String COLUMN_NAME_WEATHER_ID = "weather_id";
        public static final String COLUMN_NAME_MAIN = "main";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_DEG = "deg";
        public static final String COLUMN_NAME_CLOUDS = "clouds";
        public static final String COLUMN_NAME_RAIN = "rain";
        public static final String COLUMN_NAME_ICON = "icon";
        public static final String COLUMN_NAME_CITY_ID = "city_id";
        public static final String COLUMN_NAME_COUNT = "_count";

        public static final String SQL_CREATE_FORECASTS =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        ForecastEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ForecastEntry.COLUMN_NAME_DT + " INTEGER,"+
                        ForecastEntry.COLUMN_NAME_DAY + " INTEGER,"+
                        ForecastEntry.COLUMN_NAME_MIN + " REAL,"+
                        ForecastEntry.COLUMN_NAME_MAX + " REAL,"+
                        ForecastEntry.COLUMN_NAME_NIGHT + " REAL,"+
                        ForecastEntry.COLUMN_NAME_EVEN + " REAL," +
                        ForecastEntry.COLUMN_NAME_MORN + " REAL,"+
                        ForecastEntry.COLUMN_NAME_PRESSURE + " REAL,"+
                        ForecastEntry.COLUMN_NAME_HUMIDITY + " INTEGER,"+
                        ForecastEntry.COLUMN_NAME_WEATHER_ID + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_MAIN + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_DESCRIPTION  + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_SPEED + " REAL,"+
                        ForecastEntry.COLUMN_NAME_DEG + " INTEGER,"+
                        ForecastEntry.COLUMN_NAME_CLOUDS + " INTEGER,"+
                        ForecastEntry.COLUMN_NAME_RAIN + " REAL,"+
                        ForecastEntry.COLUMN_NAME_ICON + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_CITY_ID + " INTEGER,"+
                        "FOREIGN KEY ("+ ForecastEntry.COLUMN_NAME_CITY_ID +
                        ") REFERENCES cities ("+ CityContract.CityEntry.COLUMN_NAME_CITY_ID +"))";

        public static final String SQL_DELETE_FORECASTS =
                "DROP TABLE IF EXISTS " + ForecastEntry.TABLE_NAME;

        public static final String SQL_COUNT_FORECASTS = "SELECT " + ForecastEntry._ID +
                ", COUNT(*) AS _count FROM " + ForecastEntry.TABLE_NAME;
    }
}