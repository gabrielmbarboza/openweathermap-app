package com.gabrielmbarboza.openweathermapapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                String owmApiUrl = null;

                try {
                    owmApiUrl = wsUrl + URLEncoder.encode(location.getText().toString(), "UTF-8")
                            + "&units=metric&APPID=" + appID;

                    URL url = new URL(owmApiUrl);

                    if(url != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        WeatherTask weatherTask = new WeatherTask();
                        weatherTask.execute(url);
                    } else {
                        Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.invalid_url, Snackbar.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", e.getMessage(), e);
                }


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

    private class WeatherTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... params) {
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject weather) {
           getJsonToArrayList(weather);
            weatherArrayAdapter.notifyDataSetChanged();
            weatherLV.smoothScrollToPosition(0);
        }

        private void getJsonToArrayList(JSONObject weatherForecast) {
            weatherList.clear();

            try {
                JSONArray list = weatherForecast.getJSONArray("list");

                for (int i = 0; i < databaseList().length; i++) {
                    JSONObject day = list.getJSONObject(i);
                    JSONObject temperatures = day.getJSONObject("temp");
                    JSONObject weather = day.getJSONArray("weather").getJSONObject(0);

                    weatherList.add(new Weather(
                            day.getLong("dt"),
                            temperatures.getDouble("max"),
                            temperatures.getDouble("min"),
                            weather.getString("description"),
                            weather.getString("icon")
                    ));
                }
            } catch (JSONException e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
        }
    }
}
