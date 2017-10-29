package com.example.stan.myweatherapp;

import android.content.Context;

class WeatherSpec {
    static String getForecast(Context context, int position){
        String[] result = context.getResources().getStringArray(R.array.city_weather_description);
        return result[position];
    }
}
