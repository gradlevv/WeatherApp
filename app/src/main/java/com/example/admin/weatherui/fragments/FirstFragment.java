package com.example.admin.weatherui.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.weatherui.R;
import com.example.admin.weatherui.adapter.HoursAdapter;
import com.example.admin.weatherui.adapter.WeekAdapter;
import com.example.admin.weatherui.api.WeatherApiService;
import com.example.admin.weatherui.db.CurrentDetails;
import com.example.admin.weatherui.db.CurrentDetailsDao;
import com.example.admin.weatherui.db.Currents;
import com.example.admin.weatherui.db.CurrentsDao;
import com.example.admin.weatherui.db.DaoSession;
import com.example.admin.weatherui.db.Hour;
import com.example.admin.weatherui.db.HourDao;
import com.example.admin.weatherui.db.HoursDetails;
import com.example.admin.weatherui.db.HoursDetailsDao;
import com.example.admin.weatherui.db.WeatherIcons;
import com.example.admin.weatherui.db.WeatherIconsDao;
import com.example.admin.weatherui.db.Weeks;
import com.example.admin.weatherui.db.WeeksDao;
import com.example.admin.weatherui.db.WeeksDetails;
import com.example.admin.weatherui.db.WeeksDetailsDao;
import com.example.admin.weatherui.model.Current;
import com.example.admin.weatherui.model.Hours;
import com.example.admin.weatherui.model.HoursDatum;
import com.example.admin.weatherui.model.Week;
import com.example.admin.weatherui.model.WeekDatum;
import com.example.admin.weatherui.utils.AppController;
import com.example.admin.weatherui.utils.PreferencesHelper;
import com.example.admin.weatherui.utils.Utils;
import com.squareup.picasso.Picasso;


import org.greenrobot.greendao.query.QueryBuilder;

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

    private DrawerLayout mainLayout;

    private RecyclerView hourRecyclerView,weekRecyclerView;
    private HoursAdapter hoursAdapter;
    private WeekAdapter weekAdapter;
    private RecyclerView.LayoutManager hoursLayoutManager,weekLayoutManagerSec;

    private DaoSession daoSession;

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

        mainLayout =(DrawerLayout)view.findViewById(R.id.placeHolder);

        currentIcon = (ImageView)view.findViewById(R.id.imgCurrentIcon);
        currentIconSec =(ImageView)view.findViewById(R.id.imgCurrent);

        daoSession = ((AppController)getActivity().getApplication()).getDaoSession();

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


                    String url = Utils.urlForIcon(current.getData().get(0).getWeather().getIcon());
                    int icon = Utils.iconId(current.getData().get(0).getWeather().getIcon());

                    if (getActivity()!=null) {

//                        if (Utils.parseDate(bgSource).equals("day")){
//                            mainLayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.bg_day));
//                        }else if (Utils.parseDate(bgSource).equals("night")){
//                            mainLayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.bg_nightt));
//                        }


                        Picasso.with(getActivity().getApplicationContext())
                                .load(icon)
                                .into(currentIcon);

                        Picasso.with(getActivity().getApplicationContext())
                                .load(icon)
                                .into(currentIconSec);
                    }

                    if (checkOfflineCurrentDataExist(response.body())) {
                        addCurrentToDb(response.body());
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

                    if (checkOfflineHourlyDataExist(response.body())) {
                        addHoursToDb(response.body());
                    }

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

                    if (checkOfflineWeeklyDataExist(response.body())){
                        addWeeksToDb(response.body());
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<Week> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();
                Log.d("Week",t.getMessage());
            }
        });

    }




    private void addCurrentToDb(Current current){
        CurrentsDao currentsDao = daoSession.getCurrentsDao();
        CurrentDetailsDao currentDetailsDao = daoSession.getCurrentDetailsDao();
        WeatherIconsDao weatherIconsDao = daoSession.getWeatherIconsDao();

        Currents currents = new Currents();
        CurrentDetails currentDetails = new CurrentDetails();
        WeatherIcons weatherIcons = new WeatherIcons();

        currents.setCount(current.getCount());

        currentsDao.insert(currents);

        weatherIcons.setCode(current.getData().get(0).getWeather().getCode());
        weatherIcons.setIcon(current.getData().get(0).getWeather().getIcon());
        weatherIcons.setDescription(current.getData().get(0).getWeather().getDescription());

        weatherIconsDao.insert(weatherIcons);

        currentDetails.setRh(current.getData().get(0).getRh());
        currentDetails.setPod(current.getData().get(0).getPod());
        currentDetails.setPres(current.getData().get(0).getPres());
        currentDetails.setTimezone(current.getData().get(0).getTimezone());
        currentDetails.setCountry_code(current.getData().get(0).getCountry_code());
        currentDetails.setClouds(current.getData().get(0).getClouds());
        currentDetails.setVis(current.getData().get(0).getVis());
        currentDetails.setWind_spd(current.getData().get(0).getWind_spd());
        currentDetails.setWind_cdir_full(current.getData().get(0).getWind_cdir_full());
        currentDetails.setApp_temp(current.getData().get(0).getApp_temp());
        currentDetails.setLon(current.getData().get(0).getLon());
        currentDetails.setState_code(current.getData().get(0).getState_code());
        currentDetails.setTs(current.getData().get(0).getTs());
        currentDetails.setElev_angle(current.getData().get(0).getElev_angle());
        currentDetails.setH_angle(current.getData().get(0).getH_angle());
        currentDetails.setDewpt(current.getData().get(0).getDewpt());
        currentDetails.setOb_time(current.getData().get(0).getOb_time());
        currentDetails.setUv(current.getData().get(0).getUv());
        currentDetails.setSunset(current.getData().get(0).getSunset());
        currentDetails.setSunrise(current.getData().get(0).getSunrise());
        currentDetails.setCity_name(current.getData().get(0).getCity_name());
        currentDetails.setPrecip(current.getData().get(0).getPrecip());
        currentDetails.setStation(current.getData().get(0).getStation());
        currentDetails.setLat(current.getData().get(0).getLat());
        currentDetails.setDhi(current.getData().get(0).getDhi());
        currentDetails.setDatetime(current.getData().get(0).getDatetime());
        currentDetails.setTemp(current.getData().get(0).getTemp());
        currentDetails.setWind_dir(current.getData().get(0).getWind_dir());
        currentDetails.setSlp(current.getData().get(0).getSlp());
        currentDetails.setWind_cdir(current.getData().get(0).getWind_cdir());
        currentDetails.setCurrentsId(currents.getId());
        currentDetails.setWeatherIconsId(weatherIcons.getId());

        currentDetailsDao.insert(currentDetails);
    }

    private void addHoursToDb(Hours hours){

        HourDao hourDao = daoSession.getHourDao();
        HoursDetailsDao hoursDetailsDao = daoSession.getHoursDetailsDao();
        WeatherIconsDao weatherIconsDao = daoSession.getWeatherIconsDao();

        Hour hour = new Hour();

        hour.setCity_name(hours.getCity_name());
        hour.setCountry_code(hours.getCountry_code());
        hour.setLon(hours.getLon());
        hour.setLat(hours.getLat());
        hour.setTimezone(hours.getTimezone());
        hour.setState_code(hours.getState_code());

        hourDao.insert(hour);

        for (int i=0;i<hours.getData().size();i++){

            HoursDetails hoursDetails = new HoursDetails();
            WeatherIcons weatherIcons = new WeatherIcons();

            weatherIcons.setIcon(hours.getData().get(i).getWeather().getIcon());
            weatherIcons.setCode(hours.getData().get(i).getWeather().getCode());
            weatherIcons.setDescription(hours.getData().get(i).getWeather().getDescription());

            weatherIconsDao.insert(weatherIcons);


            hoursDetails.setWind_cdir(hours.getData().get(i).getWind_cdir());
            hoursDetails.setRh(hours.getData().get(i).getRh());
            hoursDetails.setPop(hours.getData().get(i).getPop());
            hoursDetails.setWind_cdir_full(hours.getData().get(i).getWind_cdir_full());
            hoursDetails.setApp_temp(hours.getData().get(i).getApp_temp());
            hoursDetails.setPres(hours.getData().get(i).getPres());
            hoursDetails.setDewpt(hours.getData().get(i).getDewpt());
            hoursDetails.setSnow(hours.getData().get(i).getSnow());
            hoursDetails.setUv(hours.getData().get(i).getUv());
            hoursDetails.setWind_dir(hours.getData().get(i).getWind_dir());
            hoursDetails.setPod(hours.getData().get(i).getPod());
            hoursDetails.setDhi(hours.getData().get(i).getDhi());
            hoursDetails.setTs(hours.getData().get(i).getTs());
            hoursDetails.setSnow_depth(hours.getData().get(i).getSnow_depth());
            hoursDetails.setPrecip(hours.getData().get(i).getPrecip());
            hoursDetails.setDatetime(hours.getData().get(i).getDatetime());
            hoursDetails.setTemp(hours.getData().get(i).getTemp());
            hoursDetails.setSlp(hours.getData().get(i).getSlp());
            hoursDetails.setClouds(hours.getData().get(i).getClouds());
            hoursDetails.setVis(hours.getData().get(i).getVis());
            hoursDetails.setSnow6h(hours.getData().get(i).getSnow6h());
            hoursDetails.setPrecip6h(hours.getData().get(i).getPrecip6h());
            hoursDetails.setHourId(hour.getId());
            hoursDetails.setWeatherIconsId(weatherIcons.getId());

            hoursDetailsDao.insert(hoursDetails);

        }

    }

    private void addWeeksToDb(Week week){
        WeeksDao weeksDao = daoSession.getWeeksDao();
        WeeksDetailsDao weeksDetailsDao = daoSession.getWeeksDetailsDao();
        WeatherIconsDao weatherIconsDao = daoSession.getWeatherIconsDao();

        Weeks weeks = new Weeks();

        weeks.setCity_name(week.getCity_name());
        weeks.setLon(week.getLon());
        weeks.setTimezone(week.getTimezone());
        weeks.setLat(week.getLat());
        weeks.setCountry_code(week.getCountry_code());
        weeks.setState_code(week.getState_code());

        weeksDao.insert(weeks);

        for (int i =0; i<week.getData().size();i++){
            WeeksDetails weeksDetails = new WeeksDetails();
            WeatherIcons weatherIcons = new WeatherIcons();

            weatherIcons.setIcon(week.getData().get(i).getWeather().getIcon());
            weatherIcons.setCode(week.getData().get(i).getWeather().getCode());
            weatherIcons.setDescription(week.getData().get(i).getWeather().getDescription());

            weatherIconsDao.insert(weatherIcons);

            weeksDetails.setWind_cdir(week.getData().get(i).getWind_cdir());
            weeksDetails.setRh(week.getData().get(i).getRh());
            weeksDetails.setWind_spd(week.getData().get(i).getWind_spd());
            weeksDetails.setPop(week.getData().get(i).getPop());
            weeksDetails.setWind_cdir_full(week.getData().get(i).getWind_cdir_full());
            weeksDetails.setSlp(week.getData().get(i).getSlp());
            weeksDetails.setApp_max_temp(week.getData().get(i).getApp_max_temp());
            weeksDetails.setPres(week.getData().get(i).getPres());
            weeksDetails.setDewpt(week.getData().get(i).getDewpt());
            weeksDetails.setSnow(week.getData().get(i).getSnow());
            weeksDetails.setUv(week.getData().get(i).getUv());
            weeksDetails.setTs(week.getData().get(i).getTs());
            weeksDetails.setWind_dir(week.getData().get(i).getWind_dir());
            weeksDetails.setApp_min_temp(week.getData().get(i).getApp_min_temp());
            weeksDetails.setMax_temp(week.getData().get(i).getMax_temp());
            weeksDetails.setSnow_depth(week.getData().get(i).getSnow_depth());
            weeksDetails.setPrecip(week.getData().get(i).getPrecip());
            weeksDetails.setMax_dhi(week.getData().get(i).getMax_dhi());
            weeksDetails.setDatetime(week.getData().get(i).getDatetime());
            weeksDetails.setTemp(week.getData().get(i).getTemp());
            weeksDetails.setMin_temp(week.getData().get(i).getMin_temp());
            weeksDetails.setClouds(week.getData().get(i).getClouds());
            weeksDetails.setVis(week.getData().get(i).getVis());
            weeksDetails.setWeeksId(weeks.getId());
            weeksDetails.setWeatherIconsId(weatherIcons.getId());

            weeksDetailsDao.insert(weeksDetails);
        }
    }


    // check database for existing current weather data
    private boolean checkOfflineCurrentDataExist(Current current){

        CurrentDetailsDao currentDetailsDao = daoSession.getCurrentDetailsDao();
        QueryBuilder<CurrentDetails> queryBuilder = currentDetailsDao.queryBuilder();
        queryBuilder.where(CurrentDetailsDao.Properties.City_name.eq(current.getData().get(0).getCity_name()));
        if (queryBuilder.count()>0) {
            return false;
        }else {
            return true;
        }
    }

    // check database for existing next 48hours weather data
    private boolean checkOfflineHourlyDataExist(Hours hours){

        HourDao hourDao = daoSession.getHourDao();
        QueryBuilder<Hour> queryBuilder = hourDao.queryBuilder();
        queryBuilder.where(HourDao.Properties.City_name.eq(hours.getCity_name()));
        if (queryBuilder.count()>0) {
            return false;
        }else {
            return true;
        }
    }

    // check database for existing next 7days weather data
    private boolean checkOfflineWeeklyDataExist(Week week){

        WeeksDao weeksDao = daoSession.getWeeksDao();
        QueryBuilder<Weeks> queryBuilder = weeksDao.queryBuilder();
        queryBuilder.where(WeeksDao.Properties.City_name.eq(week.getCity_name()));
        if (queryBuilder.count()>0) {
            return false;
        }else {
            return true;
        }
    }


}
