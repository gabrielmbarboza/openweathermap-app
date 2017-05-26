package com.gabrielmbarboza.openweathermapapp.db;

import android.provider.BaseColumns;

/**
 * Created by gmoraes on 23/05/17.
 */

public final class CityContract {
    private CityContract() {}

   public static class CityEntry implements BaseColumns {
       public static final String TABLE_NAME = "cities";
       public static final String COLUMN_NAME_CITY_ID = "city_id";
       public static final String COLUMN_NAME_NAME = "name";
       public static final String COLUMN_NAME_LON = "lon";
       public static final String COLUMN_NAME_LAT = "lat";
       public static final String COLUMN_NAME_COUNTRY = "country";
       public static final String COLUMN_NAME_POPULATION = "population";
       public static final String COLUMN_NAME_COUNT = "_count";

       public static final String SQL_CREATE_CITIES =
               "CREATE TABLE " + TABLE_NAME + "(" +
                       CityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                       CityEntry.COLUMN_NAME_CITY_ID + " TEXT  UNIQUE,"+
                       CityEntry.COLUMN_NAME_NAME + " TEXT,"+
                       CityEntry.COLUMN_NAME_LON + " TEXT,"+
                       CityEntry.COLUMN_NAME_LAT + " TEXT,"+
                       CityEntry.COLUMN_NAME_COUNTRY + " TEXT,"+
                       CityEntry.COLUMN_NAME_POPULATION + " TEXT)";

       public static final String SQL_DELETE_CITIES =
                "DROP TABLE IF EXISTS " + CityEntry.TABLE_NAME;

       public static final String SQL_COUNT_CITIES = "SELECT " + CityEntry._ID +
               ", COUNT(*) FROM " + CityEntry.TABLE_NAME;
   }
}
