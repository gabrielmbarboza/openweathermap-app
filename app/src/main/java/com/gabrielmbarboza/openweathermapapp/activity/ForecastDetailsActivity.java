package com.gabrielmbarboza.openweathermapapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gabrielmbarboza.openweathermapapp.R;

public class ForecastDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView descriptionDetail = (TextView) findViewById(R.id.description_detail);
        TextView minDetail = (TextView) findViewById(R.id.min_detail);
        TextView maxDetail = (TextView) findViewById(R.id.max_detail);
        ImageView conditionIV = (ImageView) findViewById(R.id.condition_detail_iv);

        Bundle bundle = getIntent().getExtras();

        String description = bundle.getString("description");
        Double min = bundle.getDouble("min");
        Double max = bundle.getDouble("max");
        String iconUrl = bundle.getString("iconUrl");

        descriptionDetail.setText(description);
        minDetail.setText("Minima: " + min + "°");
        maxDetail.setText("Máxima: " + max + "°");

        Glide.with(getBaseContext())
                .load(iconUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_error_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(conditionIV);
    }
}
