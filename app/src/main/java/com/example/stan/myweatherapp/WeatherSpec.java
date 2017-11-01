package com.example.stan.myweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

class WeatherSpec {
    static String getForecast(Context context, int position){
        String[] result = context.getResources().getStringArray(R.array.city_weather_description);
        return result[position];
    }

    static String getCityName(Context context, int position){
        String[] result = context.getResources().getStringArray(R.array.city_selection);
        return result[position];
    }

    static boolean doesIntentHaveReceivers(Context context, Intent intent){
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> receivers = packageManager.queryIntentActivities(intent, 0);
        return receivers.size() > 0;
    }
}
