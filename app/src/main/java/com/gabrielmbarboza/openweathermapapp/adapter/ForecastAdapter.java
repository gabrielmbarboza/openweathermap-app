package com.gabrielmbarboza.openweathermapapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gabrielmbarboza.openweathermapapp.R;
import com.gabrielmbarboza.openweathermapapp.db.model.Forecast;
import com.gabrielmbarboza.openweathermapapp.util.Util;

import java.util.Calendar;
import java.util.List;

/**
 * Created by gabriel on 20/05/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Forecast> forecasts;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener mClickListener;

    public ForecastAdapter(Context context, List<Forecast> forecasts) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.forecasts = forecasts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        ForecastHolder forecastHolder = new ForecastHolder(view);
        forecastHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(v);
            }
        });
        return forecastHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Forecast current = forecasts.get(position);

        ForecastHolder forecastHolder = (ForecastHolder) holder;
        long unixTime = Calendar.getInstance().getTimeInMillis() / 1000L;
        String today = Util.convertTimestampToDayOfWeek(unixTime);

        if(today.contentEquals(current.getWeekDay())) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F1F1F1"));
        }

        forecastHolder.dayTV.setText(current.getWeekDay() + ": " + current.getDescription());
        forecastHolder.maxTV.setText("Máxima: " + current.getMax() + "°");
        forecastHolder.minTV.setText("Minima: " + current.getMin() + "°");

        Glide.with(context)
                .load(current.getIconUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_error_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(forecastHolder.conditionIV);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    public static class ForecastHolder extends RecyclerView.ViewHolder {
       public TextView maxTV;
       public TextView minTV;
       public TextView dayTV;
       public ImageView conditionIV;

       public ForecastHolder(View itemView) {
           super(itemView);

           dayTV = (TextView) itemView.findViewById(R.id.day_tv);
           maxTV = (TextView) itemView.findViewById(R.id.max_tv);
           minTV = (TextView) itemView.findViewById(R.id.min_tv);
           conditionIV = (ImageView) itemView.findViewById(R.id.condition_iv);
       }
    }
}
