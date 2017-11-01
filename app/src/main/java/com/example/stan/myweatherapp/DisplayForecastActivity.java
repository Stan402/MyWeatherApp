package com.example.stan.myweatherapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class DisplayForecastActivity extends AppCompatActivity {

    public static final String CITY_TAG = "city tag";
    public static final String RESULT_TAG = "result tag";

    private String forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_forecast);

        TextView textViewForecast = (TextView) findViewById(R.id.textview_weather_forecast);
        Intent intent = getIntent();
        int cityNumber = intent.getIntExtra(CITY_TAG, 0);
        forecast = WeatherSpec.getForecast(DisplayForecastActivity.this, cityNumber);
        textViewForecast.setText(forecast);

        Button buttonShareForecast = (Button) findViewById(R.id.button_share_forecast);
        buttonShareForecast.setOnClickListener(onClickListener);
        intent.putExtra(RESULT_TAG, cityNumber);
        setResult(MainActivity.REQUEST_CODE, intent);

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

        if(WeatherSpec.doesIntentHaveReceivers(DisplayForecastActivity.this, shareIntent)) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, forecast);
            startActivity(shareIntent);
        } else
            finish();
    }
}

