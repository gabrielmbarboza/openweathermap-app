package com.gabrielmbarboza.openweathermapapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gabriel on 14/05/17.
 */

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {
    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public WeatherArrayAdapter(Context context, List<Weather> weatherForecast) {
        super(context, -1, weatherForecast);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Weather day = getItem(position);

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.conditionIV = (ImageView) convertView.findViewById(R.id.condition_iv);
            viewHolder.maxTV = (TextView) convertView.findViewById(R.id.max_tv);
            viewHolder.minTV = (TextView) convertView.findViewById(R.id.min_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(bitmaps.containsKey(day.iconUrlPath)) {
            viewHolder.conditionIV.setImageBitmap(bitmaps.get(day.iconUrlPath));
            Context context = getContext();
            viewHolder.dayTV.setText(context.getString(R.string.description, day.weekDay, day.description));
            viewHolder.maxTV.setText(context.getString(R.string.max_temp, day.max));
            viewHolder.minTV.setText(context.getString(R.string.min_temp, day.min));
        }

      return convertView;
    }

    private static class ViewHolder {
        TextView maxTV;
        TextView minTV;
        TextView dayTV;
        ImageView conditionIV;
    }
}
