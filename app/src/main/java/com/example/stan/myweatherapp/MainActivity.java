package com.example.stan.myweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CityListFragment.CityListListener, DisplayForecastFragment.OnDisplayForecastListener{

    private static final String TAG = "############";

    static final int REQUEST_CODE = 5;
    private static final String PREVIOUS_CITY_INFO = "previous city info";
    static final String FLAG_FOR_HUMIDITY = "flag for humidity";
    static final String FLAG_FOR_PRESSURE = "flag for pressure";
    static final String FLAG_FOR_TOMORROW = "flag for tomorrow";
    static final String FLAG_FOR_NEXT_WEEK = "flag for next week";
    private TextView textviewUnder;
    //private Spinner spinnerForCity;
    private CheckBox checkBoxForPressure;
    private CheckBox checkBoxForHumidity;
    private CheckBox checkBoxForTomorrow;
    private CheckBox checkBoxForNextWeek;

    private final static int VERTICAL = 1;
    private static final String SAVED_CITY = "saved_city_position";
    private static final String MY_SETTINGS = "my_settings";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewUnder = (TextView) findViewById(R.id.textview_under);
        checkBoxForHumidity = (CheckBox) findViewById(R.id.checkbox_for_humidity);
        checkBoxForPressure = (CheckBox) findViewById(R.id.checkbox_for_pressure);
        checkBoxForTomorrow = (CheckBox) findViewById(R.id.checkbox_for_tomorrow);
        checkBoxForNextWeek = (CheckBox) findViewById(R.id.checkbox_for_next_week);
        sharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);

        //View fragmentContainer = findViewById(R.id.fragment_container);
        CityListFragment cityListFragment = new CityListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, cityListFragment);
        transaction.commit();

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FLAG_FOR_HUMIDITY, checkBoxForHumidity.isChecked());
        editor.putBoolean(FLAG_FOR_PRESSURE, checkBoxForPressure.isChecked());
        editor.putBoolean(FLAG_FOR_TOMORROW, checkBoxForTomorrow.isChecked());
        editor.putBoolean(FLAG_FOR_NEXT_WEEK, checkBoxForNextWeek.isChecked());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        checkBoxForHumidity.setChecked(sharedPreferences.getBoolean(FLAG_FOR_HUMIDITY, false));
        checkBoxForPressure.setChecked(sharedPreferences.getBoolean(FLAG_FOR_PRESSURE, false));
        checkBoxForTomorrow.setChecked(sharedPreferences.getBoolean(FLAG_FOR_TOMORROW, false));
        checkBoxForNextWeek.setChecked(sharedPreferences.getBoolean(FLAG_FOR_NEXT_WEEK, false));
        //showForecastForCity();
    }

    private void showForecastForCity(int position) {
        Intent intent = new Intent(MainActivity.this, DisplayForecastActivity.class);
        intent.putExtra(DisplayForecastActivity.CITY_TAG, position);
        intent.putExtra(FLAG_FOR_HUMIDITY,checkBoxForHumidity.isChecked());
        intent.putExtra(FLAG_FOR_PRESSURE,checkBoxForPressure.isChecked());
        intent.putExtra(FLAG_FOR_TOMORROW,checkBoxForTomorrow.isChecked());
        intent.putExtra(FLAG_FOR_NEXT_WEEK,checkBoxForNextWeek.isChecked());

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

    @Override
    public void onCityListItemClick(int position) {
        DisplayForecastFragment forecastFragment = new DisplayForecastFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        forecastFragment.setCityNumber(position);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, forecastFragment);
        transaction.commit();
    }

    @Override
    public void onBackToList(int position) {
        textviewUnder.setText(String.format("The weather forecast shown for %s",
                WeatherSpec.getCityName(MainActivity.this, position)));
        CityListFragment cityListFragment = new CityListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, cityListFragment);
        transaction.commit();
    }
}
