package com.example.admin.weatherui.api;


import android.util.Log;

import com.example.admin.weatherui.utils.AppController;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static String BASE_URL="https://api.weatherbit.io/";


    public static Retrofit getRetrofit(){

        if (retrofit==null){

            //create okhhtp client for offline cache
            OkHttpClient httpClient =new OkHttpClient
                    .Builder()
                    .addInterceptor(provideOfflineCacheInterceptor())
                    .addNetworkInterceptor(provideCacheInterceptor())
                    .cache(provideCache())
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    //config retrofit offline cache
    private static Cache provideCache(){
        Cache cache = null;
        try{
            cache = new Cache(new File(AppController.getInstance().getCacheDir(),"http-cache")
                    ,10*1024*1024);

        }catch(Exception e){
            Log.d("cache exeption !",e.getMessage());
        }
        return cache;
    }

    public static Interceptor provideCacheInterceptor(){

        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( "Cache-Control", cacheControl.toString() )
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor(){

        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException{
                Request request = chain.request();

                if (!AppController.hasNetwork()){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }
}
