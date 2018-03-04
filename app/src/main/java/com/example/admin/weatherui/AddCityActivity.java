package com.example.admin.weatherui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.daimajia.swipe.util.Attributes;
import com.example.admin.weatherui.adapter.CitiesAdapter;
import com.example.admin.weatherui.db.Cities;
import com.example.admin.weatherui.db.CitiesDao;
import com.example.admin.weatherui.db.DaoSession;
import com.example.admin.weatherui.utils.AppController;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AddCityActivity extends AppCompatActivity {

    private RecyclerView cityRecyclerView;
    private ImageButton addCityButton;


    private CitiesAdapter citiesAdapter;
    private RecyclerView.LayoutManager citiesLayoutManager;

    private DaoSession daoSession;

    private List<Cities> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);


        addCityButton =(ImageButton)findViewById(R.id.addCity);

        daoSession = ((AppController)getApplication()).getDaoSession();

        CitiesDao citiesDao = daoSession.getCitiesDao();
        lst =citiesDao.queryBuilder()
                .list();

        cityRecyclerView =(RecyclerView)findViewById(R.id.citiesRecyclerView);
        cityRecyclerView.setHasFixedSize(true);
        citiesLayoutManager = new LinearLayoutManager(this);
        cityRecyclerView.setLayoutManager(citiesLayoutManager);
        cityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cityRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        citiesAdapter = new CitiesAdapter(AddCityActivity.this,AddCityActivity.this,lst);
        citiesAdapter.setMode(Attributes.Mode.Single);
        cityRecyclerView.setAdapter(citiesAdapter);


        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                palceAutoComplete();
            }
        });

        cityRecyclerView.addOnScrollListener((onScrollListener));

    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        CitiesDao citiesDao = daoSession.getCitiesDao();
        lst =citiesDao.queryBuilder()
                .list();
        citiesAdapter = new CitiesAdapter(AddCityActivity.this,AddCityActivity.this,lst);
        citiesAdapter.setMode(Attributes.Mode.Single);
        cityRecyclerView.setAdapter(citiesAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                Place place =PlaceAutocomplete.getPlace(this,data);
                double lat = place.getLatLng().latitude;
                double lon = place.getLatLng().longitude;
                String cityName = place.getAddress().toString();
                if (cityName.length()>20){
                    cityName = place.getName().toString();
                }
                addLatandLonToDb(cityName,lat,lon);
            }
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void palceAutoComplete(){

        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(AddCityActivity.this);
            startActivityForResult(intent,1);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    //insert latitude and longitude of the cities into database
    private void addLatandLonToDb(String cityName,double lat,double lon){
        CitiesDao citiesDao = daoSession.getCitiesDao();
        Cities cities = new Cities();

        cities.setCityName(cityName);
        cities.setLatitude(lat);
        cities.setLongitude(lon);

        citiesDao.insert(cities);
    }



}
