package com.gabrielmbarboza.openweathermapapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        TextView minDetail = (TextView) findViewById(R.id.min_detail);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        minDetail.setText("Mínima: " + sp.getString("minDetail", "") + "°");
    }
}
