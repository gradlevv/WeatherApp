package com.example.admin.weatherui.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.example.admin.weatherui.db.DaoMaster;
import com.example.admin.weatherui.db.DaoSession;

import org.greenrobot.greendao.database.Database;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.R;


public class AppController extends Application {

    private static AppController instance;
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();

        instance =this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"weather");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();


        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/normal-font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
   }

   public static AppController getInstance(){
       return instance;
   }

   public DaoSession getDaoSession(){
        return daoSession;
    }


   public static boolean hasNetwork () {
        return instance.isNetworkAvailable();
    }


   public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
