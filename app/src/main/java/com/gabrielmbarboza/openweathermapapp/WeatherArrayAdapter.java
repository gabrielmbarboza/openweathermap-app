package com.gabrielmbarboza.openweathermapapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gabriel on 14/05/17.
 */

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {
    private static final String TAG = "WeatherArrayAdapter";

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

        //Verifico se a imagem ja foi baixada, senao inicio uma nova thread
        if(bitmaps.containsKey(day.iconUrlPath)) {
            viewHolder.conditionIV.setImageBitmap(bitmaps.get(day.iconUrlPath));
            Context context = getContext();
            viewHolder.dayTV.setText(context.getString(R.string.description, day.weekDay, day.description));
            viewHolder.maxTV.setText(context.getString(R.string.max_temp, day.max));
            viewHolder.minTV.setText(context.getString(R.string.min_temp, day.min));
        } else {
            new LoadImageTask(viewHolder.conditionIV).execute(day.iconUrlPath);
        }

      return convertView;
    }

    private static class ViewHolder {
        TextView maxTV;
        TextView minTV;
        TextView dayTV;
        ImageView conditionIV;
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            try {
                URL imgUrl = new URL(params[0]);
                connection = (HttpURLConnection) imgUrl.openConnection();

                try (InputStream inputStream = connection.getInputStream()){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.put(params[0], bitmap);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            } finally {
                connection.disconnect();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
          imageView.setImageBitmap(bitmap);
        }
    }
}
