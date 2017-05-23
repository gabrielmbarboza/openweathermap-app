package com.gabrielmbarboza.openweathermapapp.db;

import android.provider.BaseColumns;

/**
 * Created by gmoraes on 23/05/17.
 */

public final class ForecastContract {
    private ForecastContract() {}

    private  static class ForecastEntry implements BaseColumns {
        public static final String TABLE_NAME = "forecast";
        public static final String COLUMN_NAME_CITY_ID = "city_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LON = "lon";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_POPULATION = "population";

        private static final String SQL_CREATE_FORECAST =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        ForecastEntry._ID + " INTEGER PRIMARY KEY," +
                        ForecastEntry.COLUMN_NAME_CITY_ID + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_NAME + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_LON + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_LAT + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_COUNTRY + " TEXT,"+
                        ForecastEntry.COLUMN_NAME_POPULATION + " TEXT)";

        private static final String SQL_DELETE_FORECAST =
                "DROP TABLE IF EXISTS " + ForecastEntry.TABLE_NAME;
    }
}
