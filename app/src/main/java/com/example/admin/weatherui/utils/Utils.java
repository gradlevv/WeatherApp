package com.example.admin.weatherui.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.admin.weatherui.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {


    public static String urlForIcon(String urlCode){

        StringBuilder sb = new StringBuilder();
        sb.append("https://www.weatherbit.io/static/img/icons/");
        sb.append(urlCode);
        sb.append(".png");
        return sb.toString();
    }

    public static String currentTime(String timeZone){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return dateFormat.format(date);
    }

    public static String currentDate(){
        DateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
        Date date = new Date();
        return  dateFormat.format(date);
    }

    // convert recieved time in unix timestamp and convert to localtime
    public static String timeStampConverter(int time,String timeZone){

        Date dt = new Date(time*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

        return  sdf.format(dt);
    }


    public static String convertDateToDay(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date dt = sdf.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
            return simpleDateFormat.format(dt);
        }catch (ParseException p){
            Log.d("Parse",p.getMessage());

        }
        return "";
    }


    public static String convertToLocaleDate(String date,String timeZone){

        SimpleDateFormat inputTime = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
        inputTime.setTimeZone(TimeZone.getTimeZone(timeZone));

        SimpleDateFormat outputTime= new SimpleDateFormat("HH:mm");
        try {
            Date date1 = inputTime.parse(date);
            return outputTime.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static boolean isNetworkAvilable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


    public static String parseDate(String date) {

        SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm");

        String compareStringOne = "17:00";
        String compareStringTwo = "06:00";
        String compareStringThree= "06:01";
        String compareStrinFour = "16:59";

        try {
            Date currentTime = inputParser.parse(date);
            Date compareOne = inputParser.parse(compareStringOne);
            Date compareTwo = inputParser.parse(compareStringTwo);
            Date compareThree = inputParser.parse(compareStringThree);
            Date compareFour = inputParser.parse(compareStrinFour);

            if (compareTwo.before(currentTime)&& compareOne.after(currentTime)){
                return "day";
            }else if (compareFour.before(currentTime)&& compareThree.after(currentTime)){
                return "night";
            }else{
                return "";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static int iconId(String icon){
        if (icon.equals("c01d")){
            return R.drawable.sunn;
        }else if (icon.equals("c01n")){
            return R.drawable.night;
        }else if(icon.equals("a01d")||icon.equals("a02d")||icon.equals("a03d")||icon.equals("a04d")||icon.equals("a05d")||icon.equals("a06d")){
            return R.drawable.foggy_day;
        }else if (icon.equals("a01n")||icon.equals("a02n")||icon.equals("a03n")||icon.equals("a04n")||icon.equals("a05n")||icon.equals("a06n")){
            return R.drawable.foggy_night;
        }else if (icon.equals("c04d")||icon.equals("c04n")){
            return R.drawable.cloudy;
        }else if (icon.equals("c02d")||icon.equals("c03d")){
            return R.drawable.cloudy_day;
        }else if (icon.equals("c02n")||icon.equals("c03n")){
            return R.drawable.cloudy_night;
        }else if (icon.equals("s06d")||icon.equals("s06n")){
            return R.drawable.flurries;
        }else if (icon.equals("s02d")||icon.equals("s02n")||icon.equals("s03d")||icon.equals("s03n")){
            return R.drawable.heavy_snow;
        }else if (icon.equals("s01d")||icon.equals("s04d")){
            return R.drawable.snow;
        }else if (icon.equals("s01n")){
            return R.drawable.snowy;
        }else if (icon.equals("s05d")||icon.equals("s05n")){
            return R.drawable.windy;
        }else if (icon.equals("r06d")||icon.equals("r04d")||icon.equals("f01n")||icon.equals("f01d")||icon.equals("r01d")||
        icon.equals("r01n")||icon.equals("r02d")||icon.equals("r02n")){
            return R.drawable.heavy_rain;
        }else if (icon.equals("r06n")||icon.equals("r05n")||icon.equals("r04n")){
            return R.drawable.rain_night;
        }else if (icon.equals("r05d")){
            return R.drawable.rain_light;
        }else if (icon.equals("r03d")||icon.equals("r03n")){
            return R.drawable.rain_h;
        }else if(icon.equals("d01d")||icon.equals("d02d")||icon.equals("d03d")||icon.equals("d01n")||icon.equals("d02n")||icon.equals("d03n")){
            return R.drawable.drizzle;
        }else if (icon.equals("t04d")||icon.equals("t05d")){
            return R.drawable.lightning;
        }else if (icon.equals("t01d")||icon.equals("t02d")||icon.equals("t03d")){
            return R.drawable.thunder_rain;
        }else if (icon.equals("t05n")||icon.equals("t04n")){
            return R.drawable.lightning;
        }else if (icon.equals("t01n")||icon.equals("t02n")||icon.equals("t03n")){
            return R.drawable.storm;
        }else if (icon.equals("u00d")||icon.equals("u00n")){
            return R.drawable.unknown;
        }

        return R.drawable.unknown;
    }


}
