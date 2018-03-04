package com.example.admin.weatherui.api;


import com.example.admin.weatherui.interfaces.WeatherService;

public class WeatherApiService {

    public static WeatherService getCurrent(){

      return  RetrofitClient.getRetrofit().create(WeatherService.class);
    }


}
