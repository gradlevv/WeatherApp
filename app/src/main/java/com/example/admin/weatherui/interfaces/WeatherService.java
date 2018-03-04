package com.example.admin.weatherui.interfaces;


import com.example.admin.weatherui.model.Current;
import com.example.admin.weatherui.model.Hours;
import com.example.admin.weatherui.model.Week;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("v2.0/current?")
    Call<Current> getCurrentWeather(@Query("lat")double lat,
                                    @Query("lon")double lon,
                                    @Query("key")String key);


    @GET("v2.0/forecast/hourly?")
    Call<Hours> getHoursWeather(@Query("lat")double lat,
                                @Query("lon")double lon,
                                @Query("key")String key,
                                @Query("hours")int hours);


    @GET("v2.0/forecast/daily?")
    Call<Week> getWeekWeather(@Query("lat")double lat,
                              @Query("lon")double lon,
                              @Query("days")int day,
                              @Query("key")String key);


    @GET("v2.0/current?")
    Call<Current> getCurrentWeatherWithName(@Query("city")String city,
                                            @Query("country")String country,
                                            @Query("key")String key);
}
