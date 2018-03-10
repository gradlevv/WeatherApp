package com.example.admin.weatherui.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.weatherui.R;
import com.example.admin.weatherui.adapter.HoursAdapter;
import com.example.admin.weatherui.adapter.WeekAdapter;
import com.example.admin.weatherui.api.WeatherApiService;
import com.example.admin.weatherui.model.Current;
import com.example.admin.weatherui.model.Hours;
import com.example.admin.weatherui.model.HoursDatum;
import com.example.admin.weatherui.model.Week;
import com.example.admin.weatherui.model.WeekDatum;
import com.example.admin.weatherui.utils.PreferencesHelper;
import com.example.admin.weatherui.utils.Utils;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FirstFragment extends Fragment{

    private List<HoursDatum> hours = new ArrayList<>();
    private List<WeekDatum> week = new ArrayList<>();

    private TextView currentTemp,currentHumidity,currentPrec,currentWind,currentSunrise,currentDev;
    private TextView currentCloud,currentPressure,currentSunset,currentSkyInfo,currentTime,currentDate;
    private TextView currentWindSpeed,currentWindDesc,currentMinTemp,currentMaxtemp;

    private ImageView currentIcon,currentIconSec;

    private RelativeLayout mainLayout;

    private RecyclerView hourRecyclerView,weekRecyclerView;
    private HoursAdapter hoursAdapter;
    private WeekAdapter weekAdapter;
    private RecyclerView.LayoutManager hoursLayoutManager,weekLayoutManagerSec;


    private PreferencesHelper helper;

    private double latitude,longitude;



    public static FirstFragment newInstance(double latitude,double longitude) {

        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putDouble("lat",latitude);
        args.putDouble("lon",longitude);
        fragment.setArguments(args);
        return fragment;
    }


    private void readBundle(Bundle bundle){

        if (bundle!=null){
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("lon");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_fragment, container, false);

        currentTemp =(TextView)view.findViewById(R.id.txtCurrentTemp);
        currentHumidity = (TextView)view.findViewById(R.id.txtCurrentHumidity);
        currentPrec = (TextView)view.findViewById(R.id.txtCurrentPrecipitation);
        currentWind = (TextView)view.findViewById(R.id.txtCurrentWind);
        currentSunrise =(TextView)view.findViewById(R.id.txtCurrentSunrise);
        currentDev = (TextView)view.findViewById(R.id.txtCurrentDev);
        currentCloud = (TextView)view.findViewById(R.id.txtCurrentCloud);
        currentPressure =(TextView)view.findViewById(R.id.txtCurrentPressure);
        currentSunset = (TextView)view.findViewById(R.id.txtCurrentSunset);
        currentSkyInfo =(TextView)view.findViewById(R.id.txtCurrentSkyInfo);
        currentTime = (TextView)view.findViewById(R.id.txtCurrentTime);
        currentDate =(TextView)view.findViewById(R.id.txtCurrentDate);
        currentWindDesc =(TextView)view.findViewById(R.id.currentWindDesc);
        currentWindSpeed = (TextView)view.findViewById(R.id.currentWindSpeed);
        currentMaxtemp =(TextView)view.findViewById(R.id.txtMaxTemp);
        currentMinTemp = (TextView)view.findViewById(R.id.txtMinTemp);

        mainLayout =(RelativeLayout) view.findViewById(R.id.placeHolder);

        currentIcon = (ImageView)view.findViewById(R.id.imgCurrentIcon);
        currentIconSec =(ImageView)view.findViewById(R.id.imgCurrent);


        hourRecyclerView = (RecyclerView) view.findViewById(R.id.hoursRecyclerView);
        hourRecyclerView.setHasFixedSize(true);
        hoursLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        hourRecyclerView.setLayoutManager(hoursLayoutManager);

        weekRecyclerView = (RecyclerView)view.findViewById(R.id.daysRecyclerView);
        weekRecyclerView.setHasFixedSize(true);
        weekLayoutManagerSec = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        weekRecyclerView.setLayoutManager(weekLayoutManagerSec);

        helper = new PreferencesHelper(getActivity());

        //check user location is found or not
        if (!helper.getFirstTimeLocation()){
            displayLocationWithLatLon();
        }

        return view;
    }


    // set latitude and longitude into weather methods
    private void displayLocationWithLatLon(){

        readBundle(getArguments());
        currentWeatherData(latitude,longitude);
        hourlyWeatherData(latitude,longitude);
        weeklyWeatherData(latitude,longitude);
    }




    // call api for current weather data
    private void currentWeatherData(double lat,double lon){
        Call <Current> call = WeatherApiService.getCurrent().getCurrentWeather(lat,lon,getString(R.string.api_key));
        call.enqueue(new Callback<Current>() {
            @Override
            public void onResponse(@NonNull Call<Current> call, @NonNull Response<Current> response) {
                if (response.isSuccessful()){
                    Current current = response.body();
                    currentTemp.setText(String.valueOf(Math.round(current.getData().get(0).getTemp())));
                    currentHumidity.setText(current.getData().get(0).getRh()+"%");
                    currentPrec.setText(current.getData().get(0).getPrecip()+" mm");
                    currentDev.setText(String.valueOf(current.getData().get(0).getDewpt()));
                    currentCloud.setText(current.getData().get(0).getClouds()+"%");
                    currentPressure.setText(current.getData().get(0).getPres()+" mbr");
                    currentSkyInfo.setText(current.getData().get(0).getWeather().getDescription());
                    currentWindSpeed.setText(String.valueOf(Math.round(current.getData().get(0).getWind_spd()*3.6))+"km/h");
                    currentWindDesc.setText(current.getData().get(0).getWind_cdir_full());
                    String timeZone= current.getData().get(0).getTimezone();
                    String[] tZone = timeZone.split("\\\\");
                    String newTimeZone = tZone[0];
                    currentSunrise.setText(Utils.convertToLocaleDate(current.getData().get(0).getSunrise(),newTimeZone));
                    currentSunset.setText(Utils.convertToLocaleDate(current.getData().get(0).getSunset(),newTimeZone));
                    currentTime.setText(Utils.currentTime(newTimeZone));
                    currentDate.setText(Utils.currentDate());

                    String bgSource =currentTime.getText().toString();
                    int icon = Utils.iconId(current.getData().get(0).getWeather().getIcon());

                    if (getActivity()!=null) {

//                        if (Utils.parseDate(bgSource).equals("day")){
//                            mainLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_day));
//                        }else if (Utils.parseDate(bgSource).equals("night")){
//                            mainLayout.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_nightt));
//                        }


                        Picasso.with(getActivity().getApplicationContext())
                                .load(icon)
                                .into(currentIcon);

                        Picasso.with(getActivity().getApplicationContext())
                                .load(icon)
                                .into(currentIconSec);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<Current> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
                Log.d("current",t.getMessage());
            }
        });
    }

    //call api for next 48hours weather data
    private void hourlyWeatherData(double lat,double lon) {

        Call<Hours> call = WeatherApiService.getCurrent().getHoursWeather(lat, lon,
                getString(R.string.api_key),48);

        call.enqueue(new Callback<Hours>() {
            @Override
            public void onResponse(@NonNull Call<Hours> call, @NonNull Response<Hours> response) {

                if(response.isSuccessful()) {
                    hours.addAll(response.body().getData());
                    hoursAdapter = new HoursAdapter(getActivity(), hours,response.body());
                    hourRecyclerView.setAdapter(hoursAdapter);
                    hoursAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Hours> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
                Log.d("Log",t.getMessage());
            }
        });
    }

    //call api for next 7days weather data
    private void weeklyWeatherData(double lat,double lon){
        Call<Week> call = WeatherApiService.getCurrent().getWeekWeather(lat,lon,7,getString(R.string.api_key));
        call.enqueue(new Callback<Week>() {
            @Override
            public void onResponse(@NonNull Call<Week> call, @NonNull Response<Week> response) {
                if (response.isSuccessful()){
                    currentMinTemp.setText(String.valueOf(Math.round(response.body().getData().get(0).getMin_temp()))+"°");
                    currentMaxtemp.setText(String.valueOf(Math.round(response.body().getData().get(0).getMax_temp()))+"°");
                    week.addAll(response.body().getData());
                    weekAdapter = new WeekAdapter(getActivity(),week);
                    weekRecyclerView.setAdapter(weekAdapter);
                    weekAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Week> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
                Log.d("Week",t.getMessage());
            }
        });

    }


}
