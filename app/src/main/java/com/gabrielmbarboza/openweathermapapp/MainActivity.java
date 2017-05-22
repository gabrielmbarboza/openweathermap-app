package com.gabrielmbarboza.openweathermapapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;

import android.os.AsyncTask;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private WeatherAdapter weatherAdapter;
    private RecyclerView recyclerView;
    private String weatherURL;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String city = preferences.getString("city", "");
        String unit = preferences.getString("degree_scale", "metric");
        String appID = getText(R.string.app_id).toString();
        String wsUrl = getText(R.string.ws_url).toString();

        try {
            weatherURL = wsUrl + URLEncoder.encode(city, "UTF-8")
                    + "&units=" + unit + "&APPID=" + appID + "&cnt=7&lang=pt";
            Log.d("URL: ", weatherURL);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                new WeatherTask().execute();
            }
        });

        new WeatherTask().execute();

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
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class WeatherTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        HttpURLConnection connection = null;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL(weatherURL);
            }
            catch (MalformedURLException e) {
                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        "Erro no formato da URL", Snackbar.LENGTH_LONG).show();
                Log.e("MainActivity", e.getMessage(), e);
            }

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();
                } else {
                    return ("connection fail");
                }

            } catch (IOException ioe) {
                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        "Erro no formato da URL", Snackbar.LENGTH_LONG).show();
                Log.e("MainActivity", ioe.getMessage(), ioe);
            } finally {
                connection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            getJsonToArrayList(result);
            swipeRefreshLayout.setRefreshing(false);
        }

        private void getJsonToArrayList(String result) {
            List<Weather> weatherList = new ArrayList<Weather>();

            try {
                JSONObject json =  new JSONObject(result);

                JSONArray list = json.getJSONArray("list");
                Log.d("JSONARRAY: ", list.toString());
                for (int i = 0; i < list.length(); i++) {
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
                recyclerView = (RecyclerView) findViewById(R.id.weather_rv);
                weatherAdapter = new WeatherAdapter(MainActivity.this, weatherList);
                recyclerView.setAdapter(weatherAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            } catch (JSONException e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
        }
    }
}
