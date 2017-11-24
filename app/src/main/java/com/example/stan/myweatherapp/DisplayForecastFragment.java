package com.example.stan.myweatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class DisplayForecastFragment extends Fragment {

    private static final String SAVED_CITY = "saved_city_position";

    private OnDisplayForecastListener mListener;

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


    public DisplayForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_forecast, container, false);
        if (savedInstanceState != null){
            cityNumber = savedInstanceState.getInt(SAVED_CITY);
        }

        textviewForecast = (TextView) view.findViewById(R.id.textview_weather_forecast_fragment);
        textViewTomorrowForecast = (TextView) view.findViewById(R.id.textview_tomorrow_forecast_fragment);
        textViewNextWeekForecast = (TextView) view.findViewById(R.id.textview_next_week_forecast_fragment);

        showPressure = ((CheckBox) getActivity().findViewById(R.id.checkbox_for_pressure)).isChecked();
        showHumidity = ((CheckBox) getActivity().findViewById(R.id.checkbox_for_humidity)).isChecked();
        showTomorrowForecast = ((CheckBox) getActivity().findViewById(R.id.checkbox_for_tomorrow)).isChecked();
        showNextWeekForecast = ((CheckBox) getActivity().findViewById(R.id.checkbox_for_next_week)).isChecked();

        Button buttonReturnToList = (Button) view.findViewById(R.id.button_return_to_city_list);
        buttonReturnToList.setOnClickListener(onClickListener);
        return view;
    }

    @Override
    public void onResume() {

        forecastForToday = WeatherSpec.getForecast(getActivity()
                , cityNumber, showPressure, showHumidity);
        if (showTomorrowForecast)
            forecastForTomorrow = WeatherSpec.getTomorrowForecast(getActivity()
                    , cityNumber, showPressure, showHumidity);
        if (showNextWeekForecast)
            forecastForNextWeek = WeatherSpec.getNextWeekForecast(getActivity()
                    , cityNumber, showPressure, showHumidity);
        textviewForecast.setText(forecastForToday);
        textViewTomorrowForecast.setText(forecastForTomorrow);
        textViewNextWeekForecast.setText(forecastForNextWeek);

        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayForecastListener) {
            mListener = (OnDisplayForecastListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDisplayForecastListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDisplayForecastListener {
        // TODO: Update argument type and name
        void onBackToList(int position);
    }

    public void setCityNumber(int cityNumber) {
        this.cityNumber = cityNumber;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mListener.onBackToList(cityNumber);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_CITY, cityNumber);
    }
}
