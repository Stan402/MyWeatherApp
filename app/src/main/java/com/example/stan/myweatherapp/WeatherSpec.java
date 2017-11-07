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


    static String getForecast(Context context, int cityNumber, boolean showPressure, boolean showHumidity) {
        StringBuilder forecast = new StringBuilder("Today the weather in ");
        forecast.append(getCityName(context, cityNumber));
        forecast.append(" is \n");
        fillMockData(context, forecast, cityNumber, showPressure, showHumidity);
        return forecast.toString();
    }


    static String getTomorrowForecast(Context context, int cityNumber, boolean showPressure, boolean showHumidity) {
        StringBuilder forecast = new StringBuilder("Tomorrow the weather in ");
        forecast.append(getCityName(context, cityNumber));
        forecast.append(" expected to be \n");
        fillMockData(context, forecast, cityNumber, showPressure, showHumidity);
        return forecast.toString();
    }

    static String getNextWeekForecast(Context context, int cityNumber, boolean showPressure, boolean showHumidity) {
        StringBuilder forecast = new StringBuilder("Next week the weather in ");
        forecast.append(getCityName(context, cityNumber));
        forecast.append(" expected to be \n");
        fillMockData(context, forecast, cityNumber, showPressure, showHumidity);
        return forecast.toString();
    }

    private static void fillMockData(Context context, StringBuilder forecast, int cityNumber
            , boolean showPressure, boolean showHumidity) {
        forecast.append(getForecast(context, cityNumber));
        if (showPressure)
            forecast.append("\nIncluding pressure data");
        if (showHumidity)
            forecast.append("\nIncluding humidity data");
    }
}
