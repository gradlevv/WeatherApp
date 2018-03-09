package com.example.admin.weatherui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SplashActivity extends AppCompatActivity {

    private TextView logoName,logoDesc;
    private ImageView logoImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoName = (TextView)findViewById(R.id.name);
        logoDesc = (TextView)findViewById(R.id.desc);
        logoImg = (ImageView)findViewById(R.id.logo);


        Animation animation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        logoImg.startAnimation(animation);
        logoName.startAnimation(animation);
        logoDesc.startAnimation(animation);

        final Intent intent = new Intent(this,MainActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
