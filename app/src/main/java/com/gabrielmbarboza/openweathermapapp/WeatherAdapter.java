package com.gabrielmbarboza.openweathermapapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

/**
 * Created by gabriel on 20/05/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Weather> weatherList;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener mClickListener;

    public WeatherAdapter(Context context, List<Weather> weatherList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.weatherList = weatherList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        WeatherHolder weatherHolder = new WeatherHolder(view);
        weatherHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(v);
            }
        });
        return weatherHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Weather current = weatherList.get(position);

        WeatherHolder weatherHolder = (WeatherHolder) holder;
        long unixTime = Calendar.getInstance().getTimeInMillis() / 1000L;
        String today = Util.convertTimestampToDayOfWeek(unixTime);
        Log.d("COLOR: ", today);
        if(today.contentEquals(current.weekDay)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#B0C4DE"));
        }

        weatherHolder.dayTV.setText(current.weekDay + ": " + current.description);
        weatherHolder.maxTV.setText("Máxima: " + current.max + "°");
        weatherHolder.minTV.setText("Minima: " + current.min + "°");

        Glide.with(context).load(current.iconUrl)
                .placeholder(R.drawable.ic_error_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(weatherHolder.conditionIV);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    public static class WeatherHolder extends RecyclerView.ViewHolder {
       public TextView maxTV;
       public TextView minTV;
       public TextView dayTV;
       public ImageView conditionIV;

       public WeatherHolder(View itemView) {
           super(itemView);

           dayTV = (TextView) itemView.findViewById(R.id.day_tv);
           maxTV = (TextView) itemView.findViewById(R.id.max_tv);
           minTV = (TextView) itemView.findViewById(R.id.min_tv);
           conditionIV = (ImageView) itemView.findViewById(R.id.condition_iv);
       }
    }
}
