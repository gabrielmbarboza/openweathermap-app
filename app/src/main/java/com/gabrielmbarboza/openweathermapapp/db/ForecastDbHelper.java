package com.gabrielmbarboza.openweathermapapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gabriel on 23/05/17.
 */

public class ForecastDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "forecast.db";

    public ForecastDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ForecastContract.ForecastEntry.SQL_CREATE_FORECASTS);
        db.execSQL(CityContract.CityEntry.SQL_CREATE_CITIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ForecastContract.ForecastEntry.SQL_DELETE_FORECASTS);
        db.execSQL(CityContract.CityEntry.SQL_DELETE_CITIES);
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
