package com.example.stan.myweatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayForecastActivity extends AppCompatActivity {

    private static final String TAG = "DDDDDDDDDD";

    public static final String CITY_TAG = "city tag";
    public static final String RESULT_TAG = "result tag";

    private TextView textviewForecast;
    private TextView textViewTomorrowForecast;
    private TextView textViewNextWeekForecast;
    private int cityNumber;

    private String forecastForToday;
    private String forecastForTomorrow;
    private String forecastForNextWeek;

    private boolean showPressure;
    private boolean showHumidity;
    private boolean showTomorrowForecast;
    private boolean showNextWeekForecast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_forecast);

        textviewForecast = (TextView) findViewById(R.id.textview_weather_forecast);
        textViewTomorrowForecast = (TextView) findViewById(R.id.textview_tomorrow_forecast);
        textViewNextWeekForecast = (TextView) findViewById(R.id.textview_next_week_forecast);

        Intent intent = getIntent();
        cityNumber = intent.getIntExtra(CITY_TAG, 0);
        showPressure = intent.getBooleanExtra(MainActivity.FLAG_FOR_PRESSURE, false);
        showHumidity = intent.getBooleanExtra(MainActivity.FLAG_FOR_HUMIDITY, false);
        showTomorrowForecast = intent.getBooleanExtra(MainActivity.FLAG_FOR_TOMORROW, false);
        showNextWeekForecast = intent.getBooleanExtra(MainActivity.FLAG_FOR_NEXT_WEEK, false);

        Button buttonShareForecast = (Button) findViewById(R.id.button_share_forecast);
        buttonShareForecast.setOnClickListener(onClickListener);
        intent.putExtra(RESULT_TAG, cityNumber);
        setResult(MainActivity.REQUEST_CODE, intent);

    }

    @Override
    protected void onResume() {
        forecastForToday = WeatherSpec.getForecast(DisplayForecastActivity.this
                , cityNumber, showPressure, showHumidity);
        if (showTomorrowForecast)
            forecastForTomorrow = WeatherSpec.getTomorrowForecast(DisplayForecastActivity.this
                    , cityNumber, showPressure, showHumidity);
        if (showNextWeekForecast)
            forecastForNextWeek = WeatherSpec.getNextWeekForecast(DisplayForecastActivity.this
                    , cityNumber, showPressure, showHumidity);
        textviewForecast.setText(forecastForToday);
        textViewTomorrowForecast.setText(forecastForTomorrow);
        textViewNextWeekForecast.setText(forecastForNextWeek);
        super.onResume();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            shareForecast();
        }
    };

    private void shareForecast() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if (WeatherSpec.doesIntentHaveReceivers(DisplayForecastActivity.this, shareIntent)) {
            String message = forecastForToday + "\n" +
                    forecastForTomorrow + "\n" + forecastForNextWeek;
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(shareIntent);
        } else
            finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"onSaveInstanceState...");
        outState.putBoolean(MainActivity.FLAG_FOR_PRESSURE, showPressure);
        outState.putBoolean(MainActivity.FLAG_FOR_HUMIDITY, showHumidity);
        outState.putBoolean(MainActivity.FLAG_FOR_TOMORROW, showTomorrowForecast);
        outState.putBoolean(MainActivity.FLAG_FOR_NEXT_WEEK, showNextWeekForecast);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState...");
        showPressure = savedInstanceState.getBoolean(MainActivity.FLAG_FOR_PRESSURE, false);
        showHumidity = savedInstanceState.getBoolean(MainActivity.FLAG_FOR_HUMIDITY, false);
        showTomorrowForecast = savedInstanceState.getBoolean(MainActivity.FLAG_FOR_TOMORROW, false);
        showNextWeekForecast = savedInstanceState.getBoolean(MainActivity.FLAG_FOR_NEXT_WEEK, false);
        super.onRestoreInstanceState(savedInstanceState);
    }
}

