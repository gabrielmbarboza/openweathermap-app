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
    private String owmUrl;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String city = preferences.getString("city", "Vitória,BR");
        String unit = preferences.getString("degree_scale", "metric");
        String appID = getText(R.string.app_id).toString();
        String wsUrl = getText(R.string.ws_url).toString();

        try {
            owmUrl = wsUrl + URLEncoder.encode(city, "UTF-8")
                    + "&units=" + unit + "&APPID=" + appID + "&cnt=7&lang=pt";
            Log.d("URL: ", owmUrl);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                new ForecastTask().execute();
            }
        });

        new ForecastTask().execute();

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.no_conectivity);
        builder.setMessage(R.string.no_conectivity_message);

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

    private class GetResult {
        City city;
        List<Forecast> forecasts;

        public GetResult(City city, List<Forecast> forecasts) {
            this.city = city;
            this.forecasts = forecasts;
        }

    }

    private class ForecastTask extends AsyncTask<String, Void, List<Forecast>> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        HttpURLConnection connection = null;
        URL url = null;

        @Override
        protected List<Forecast> doInBackground(String... params) {

            ForecastDbHelper dbHelper = new ForecastDbHelper(getBaseContext());
            ForecastDBOperations forecastOps = new ForecastDBOperations(dbHelper);

            int forecastCount = forecastOps.getCount();

            if(forecastCount == 0 && !getConnectivity()) {
                showAlertDialog();
            } else if (forecastCount == 0 && getConnectivity()) {
                String result = getResultJSON();

                if (result != null) {
                    GetResult gResult = getJsonToArrayList(result);
                    List<Forecast> forecastList = gResult.forecasts;
                    City city =  gResult.city;

                    CityDBOperations cityOps = new CityDBOperations(dbHelper);
                    cityOps.addCity(city);

                    if(!forecastList.isEmpty()) {
                        forecastOps.addAll(forecastList);
                    }
                }
            }

            List<Forecast> forecasts = forecastOps.getAllForecasts();

            return forecasts;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        private String getResultJSON() {
            StringBuilder result = new StringBuilder();

            try {
                url = new URL(owmUrl);
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

                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
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

            return result.toString();
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            progressDialog.dismiss();
            addForecastsToAdapter(forecasts);
            swipeRefreshLayout.setRefreshing(false);
        }

        private GetResult getJsonToArrayList(String result) {
            List<Forecast> forecasts = new ArrayList<Forecast>();
            City city = new City();

            try {
                if(result != null) {
                    JSONObject json =  new JSONObject(result);

                    JSONObject cityObj = json.getJSONObject("city");
                    JSONObject coordObj = cityObj.getJSONObject("coord");


                    city.setCityId(cityObj.getString("id"));
                    city.setName(cityObj.getString("name"));
                    city.setLon(coordObj.getString("lon"));
                    city.setLat(coordObj.getString("lat"));
                    city.setCountry(cityObj.getString("country"));
                    city.setPopulation(cityObj.getDouble("population"));

                    JSONArray list = json.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject obj = list.getJSONObject(i);
                        JSONObject temp = obj.getJSONObject("temp");
                        JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);

                        Forecast forecast = new Forecast(
                                 obj.getInt("dt"), temp.getInt("day"), temp.getDouble("min"),
                                 temp.getDouble("max"), temp.getDouble("night"), temp.getDouble("eve"),
                                 temp.getDouble("morn"), obj.getDouble("pressure"), obj.getInt("humidity"),
                                 weather.getString("id"), weather.getString("main"), weather.getString("description"),
                                 obj.getDouble("speed"), obj.getInt("deg"), obj.getInt("clouds"), 0.0d,
                                 weather.getString("icon"), city
                        );

                        forecasts.add(forecast);
                    }
                }

            } catch (JSONException e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return (new GetResult(city, forecasts));
        }

        private void addForecastsToAdapter(final List<Forecast> forecasts) {
            recyclerView = (RecyclerView) findViewById(R.id.weather_rv);
            forecastAdapter = new ForecastAdapter(MainActivity.this, forecasts);
            forecastAdapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = recyclerView.indexOfChild(v);
                    Forecast forecast = forecasts.get(pos);

                    Intent detailsIntent = new Intent(MainActivity.this, ForecastDetailsActivity.class);

                    detailsIntent.putExtra("description", forecast.getCity().getName() +", " +forecast.getWeekDay() +": "+forecast.getDescription());
                    detailsIntent.putExtra("min", forecast.getMin());
                    detailsIntent.putExtra("max", forecast.getMax());
                    detailsIntent.putExtra("iconUrl", forecast.getIconUrl());
                    startActivity(detailsIntent);
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(forecastAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        }
    }
}
