package com.example.stan.myweatherapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CityListFragment extends Fragment {

    private final static int VERTICAL = 1;
    private CityListListener cityListListener;

    interface CityListListener {
        void onCityListItemClick(int position);
    }

    @Override
    public void onAttach(Context context) {
        cityListListener = (CityListListener) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_city_list, container, false);

        RecyclerView cityRecyclerView = rootView.findViewById(R.id.city_recycler_view); //Найдем наш RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //Создадим LinearLayoutManager
        layoutManager.setOrientation(VERTICAL);//Обозначим ориентацию
        cityRecyclerView.setLayoutManager(layoutManager);//Назначим нашему RecyclerView созданный ранее layoutManager
        cityRecyclerView.setAdapter(new MyAdapter());//Назначим нашему RecyclerView адаптер
        return rootView;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new MyViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return WeatherSpec.getCityCount(getActivity());
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityNameTextView;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.city_list_item, parent, false));
            itemView.setOnClickListener(this);
            cityNameTextView = (TextView) itemView.findViewById(R.id.city_name_text_view);
        }

        public void bind(int position) {
            String cityName = WeatherSpec.getCityName(getActivity(), position);
            cityNameTextView.setText(cityName);
        }

        @Override
        public void onClick(View v) {
            cityListListener.onCityListItemClick(getLayoutPosition());
        }
    }


}
