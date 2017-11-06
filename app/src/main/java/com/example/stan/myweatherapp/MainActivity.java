package com.example.stan.myweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "############";

    static final int REQUEST_CODE = 5;
    private static final String PREVIOUS_CITY_INFO = "previous city info";
    private TextView textviewUnder;
    private Spinner spinnerForCity;

    private static final String SAVED_CITY = "saved_city_position";
    private static final String MY_SETTINGS = "my_settings";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewUnder = (TextView) findViewById(R.id.textview_under);
        Button showForecastButton = (Button) findViewById(R.id.button_show_forecast);
        spinnerForCity = (Spinner) findViewById(R.id.spinner_for_city);
        showForecastButton.setOnClickListener(onClickListener);
        sharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button_show_forecast) {
                showForecastForCity();
            }
        }
    };

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_CITY, spinnerForCity.getSelectedItemPosition());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        spinnerForCity.setSelection(sharedPreferences.getInt(SAVED_CITY, 0));
        //showForecastForCity();
    }

    private void showForecastForCity() {
        //textviewForecast.setText(WeatherSpec.getForecast(MainActivity.this, spinnerForCity.getSelectedItemPosition()));
        Intent intent = new Intent(MainActivity.this, DisplayForecastActivity.class);
        intent.putExtra(DisplayForecastActivity.CITY_TAG, spinnerForCity.getSelectedItemPosition());
        startActivityForResult(intent, REQUEST_CODE);
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            int cityNumber = data.getIntExtra(DisplayForecastActivity.RESULT_TAG, 0);
            textviewUnder.setText(String.format("The weather forecast shown for %s",
                    WeatherSpec.getCityName(MainActivity.this, cityNumber)));
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState:" + savedInstanceState);
        if (savedInstanceState != null)
            textviewUnder.setText(savedInstanceState.getCharSequence(PREVIOUS_CITY_INFO));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        //Я не стал сохранять остальные настройки, потому что они и так восстанавливаются из sharedPreferance
        //Единственное что не сохраняется при пересоздании это инфа о предыдущем просмотре - ее и сохраняем
        outState.putCharSequence(PREVIOUS_CITY_INFO, textviewUnder.getText());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG,"onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}
