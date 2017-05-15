package com.gabrielmbarboza.openweathermapapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gabriel on 14/05/17.
 */

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {
    public WeatherArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    private static class ViewHolder {
        TextView maxTV;
        TextView minTV;
        TextView dayTV;
        ImageView conditionIV;
    }
}
