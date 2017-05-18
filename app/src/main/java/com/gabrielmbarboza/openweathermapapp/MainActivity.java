package com.gabrielmbarboza.openweathermapapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WeatherArrayAdapter weatherArrayAdapter;
    private ListView weatherLV;
    private List<Weather> weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weatherLV = (ListView) findViewById(R.id.weather_lv);
        weatherArrayAdapter = new WeatherArrayAdapter(this, weatherList);
        weatherLV.setAdapter(weatherArrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText location = (EditText) findViewById(R.id.location_et);
                String appID = getString(R.string.app_id);
                String wsUrl = getString(R.string.ws_url);

                try {
                    String owmApiUrl = wsUrl + URLEncoder.encode(location.getText().toString(), "UTF-8")
                            + "&units=metric&APPID=" + appID;
                } catch (Exception e) {
                    Log.e("MainActivity", e.getMessage(), e);
                }
                //Url url =
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
