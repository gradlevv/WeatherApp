package com.example.admin.weatherui.utils;

import android.app.Application;


import com.example.admin.weatherui.db.DaoMaster;
import com.example.admin.weatherui.db.DaoSession;

import org.greenrobot.greendao.database.Database;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.R;


public class AppController extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

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



   public DaoSession getDaoSession(){
        return daoSession;
    }
}
