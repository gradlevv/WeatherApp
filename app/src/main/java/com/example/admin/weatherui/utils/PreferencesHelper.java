package com.example.admin.weatherui.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

public class PreferencesHelper {

    private static String FIRST_TIME_LOCATION="firstTime";
    private static String SAVE_CURRENT_WEATHER_DATA="saveCurrent";
    private static String SAVE_HOURLY_WEATHER_DATA="saveHourly";
    private static String SAVE_WEEKLY_WEATHER_DATA="saveWeekly";
    private static String FIRST_TIME_LAT="firstLat";

    SharedPreferences preferences;

    public PreferencesHelper(Activity activity) {
        preferences=activity.getPreferences(activity.MODE_PRIVATE);
    }

    public boolean getFirstTimeLocation(){
        return preferences.getBoolean(FIRST_TIME_LOCATION,true);
    }

    public void setFirstTimeLocation(boolean saveState){
        preferences.edit().putBoolean(FIRST_TIME_LOCATION,saveState).apply();
    }

    public boolean getSaveCurrentWeatherDataForFirstTime(){
        return preferences.getBoolean(SAVE_CURRENT_WEATHER_DATA,true);
    }


    public void setSaveCurrentWeatherDataForFirstTime(boolean saveState){
        preferences.edit().putBoolean(SAVE_CURRENT_WEATHER_DATA,saveState).apply();
    }

    public boolean getSaveHourlyWeatherDataForFirstTime(){
        return preferences.getBoolean(SAVE_HOURLY_WEATHER_DATA,true);
    }


    public void setSaveHourlyWeatherDataForFirstTime(boolean saveState){
        preferences.edit().putBoolean(SAVE_HOURLY_WEATHER_DATA,saveState).apply();
    }

    public boolean getSaveWeekWeatherDataForFirstTime(){
        return preferences.getBoolean(SAVE_WEEKLY_WEATHER_DATA,true);
    }


    public void setSaveWeekWeatherDataForFirstTime(boolean saveState){
        preferences.edit().putBoolean(SAVE_WEEKLY_WEATHER_DATA,saveState).apply();
    }

}
