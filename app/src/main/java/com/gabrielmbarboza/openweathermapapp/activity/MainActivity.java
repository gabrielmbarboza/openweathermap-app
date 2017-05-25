package com.gabrielmbarboza.openweathermapapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.os.AsyncTask;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gabrielmbarboza.openweathermapapp.R;
import com.gabrielmbarboza.openweathermapapp.Weather;
import com.gabrielmbarboza.openweathermapapp.adapter.ForecastAdapter;
import com.gabrielmbarboza.openweathermapapp.db.CityDBOperations;
import com.gabrielmbarboza.openweathermapapp.db.ForecastDBOperations;
import com.gabrielmbarboza.openweathermapapp.db.ForecastDbHelper;
import com.gabrielmbarboza.openweathermapapp.db.model.City;
import com.gabrielmbarboza.openweathermapapp.db.model.Forecast;

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
    private ForecastAdapter forecastAdapter;
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

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Sem conectividade!");
        builder.setMessage("Você não possuí dados offline e não está conectado a rede");

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();

        alert.show();
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

    private boolean getConnectivity() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            connected = true;
        }

        return connected;
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

            ForecastDbHelper dbHelper = new ForecastDbHelper(getBaseContext());
            ForecastDBOperations forecastOps = new ForecastDBOperations(dbHelper);

            int forecastCount = forecastOps.getCount();

            if(forecastCount == 0 && !getConnectivity()) {
                showAlertDialog();
            } else if (forecastCount == 0 && getConnectivity()) {

            }

            CityDBOperations cityOp = new CityDBOperations(dbHelper);

            //cityOp.addCity(new City("3444924", "Vitoria", "-40.3378", "-20.3195", "BR", 358.875));
            //City cityTest = cityOp.getCity("3444924");

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

            try {
                url = new URL(weatherURL);
            }
            catch (MalformedURLException e) {
                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        "Erro no formato da URL", Snackbar.LENGTH_LONG).show();
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            getJsonToArrayList(result);
            swipeRefreshLayout.setRefreshing(false);
        }

        private List<Forecast> getJsonToArrayList(String result) {
            List<Forecast> forecasts = new ArrayList<Forecast>();

            try {
                if(result != null) {

                    JSONObject json =  new JSONObject(result);

                    JSONObject cityObj = json.getJSONObject("city");
                    JSONObject coordObj = cityObj.getJSONObject("city");

                    City city = new City(
                            cityObj.getString("id"),
                            cityObj.getString("name"),
                            cityObj.getString("lon"),
                            cityObj.getString("lat"),
                            cityObj.getString("country"),
                            cityObj.getDouble("population")
                    );

                    JSONArray list = json.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject obj = list.getJSONObject(i);
                        JSONObject temperatures = obj.getJSONObject("temp");
                        JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);

                        /*Forecast forecast = new Forecast(
                                 weather.getLong("dt"),
                        int day,
                        Double min,
                        Double max,
                        Double night,
                        Double even,
                        Double morn,
                        Double pressure,
                        int humidity,
                        int weatherId,
                        String main,
                        String description,
                        Double speed,
                        int deg,
                        int clouds,
                        Double rain, String icon,
                                City city
                        );


                        forecasts.add(new Forecast(
                                obj.getLong("dt"),
                                temperatures.getDouble("max"),
                                temperatures.getDouble("min"),
                                weather.getString("description"),
                                weather.getString("icon")

                        ));*/
                    }
                }

            } catch (JSONException e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return forecasts;
        }

        private void addForecastsToAdapter(List<Forecast> forecasts) {
            recyclerView = (RecyclerView) findViewById(R.id.weather_rv);
            forecastAdapter = new ForecastAdapter(MainActivity.this, forecasts);
            forecastAdapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = recyclerView.indexOfChild(v);
                    Log.d("ITEM POSITION:", " " + pos);
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(forecastAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        }
    }
}
