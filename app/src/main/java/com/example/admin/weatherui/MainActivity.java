package com.example.admin.weatherui;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.weatherui.adapter.MyPagerAdapter;
import com.example.admin.weatherui.config.Config;
import com.example.admin.weatherui.db.Cities;
import com.example.admin.weatherui.db.CitiesDao;
import com.example.admin.weatherui.db.DaoSession;
import com.example.admin.weatherui.fragments.FirstFragment;
import com.example.admin.weatherui.utils.AppController;
import com.example.admin.weatherui.utils.PreferencesHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.rd.PageIndicatorView;

import org.greenrobot.greendao.query.DeleteQuery;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener{

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private BroadcastReceiver registrationBroadcastReceiver;

    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    private ViewPager vpPager;
    private MyPagerAdapter adapterViewPager;
    private ActionBar actionBar;
    private Toolbar toolBar;
    private PageIndicatorView indicatorView;
    private TextView currentCity;

    private PreferencesHelper helper;
    private DaoSession daoSession;
    private SparseArray<FirstFragment> citiesList;

    private int adapterPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentCity= (TextView)findViewById(R.id.currentCity);
        vpPager = (ViewPager) findViewById(R.id.viewPager);
        toolBar =(Toolbar)findViewById(R.id.toolBar);
        indicatorView=(PageIndicatorView)findViewById(R.id.pageIndicator);
        setSupportActionBar(toolBar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        daoSession = ((AppController)getApplication()).getDaoSession();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PLAY_SERVICES_RESOLUTION_REQUEST);
        }

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }

        CitiesDao citiesDao = daoSession.getCitiesDao();
        List<Cities> lst =citiesDao.queryBuilder()
                .list();
        if (lst.size()>0){
            currentCity.setText(lst.get(0).getCityName());
        }

        registrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.STR_PUSH)) {
                    String message = intent.getStringExtra("message");
                    showNotification("Weather Alert!!",message);
                }
            }
        };

        citiesList = new SparseArray<>();
        citiesList= fetchLatLonFromDb();
        indicatorView.setCount(citiesList.size());


    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.sun)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        helper = new PreferencesHelper(this);
        if (helper.getFirstTimeLocation()) {
            displayLocation();
            helper.setFirstTimeLocation(false);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Tag", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(MainActivity.this,AddCityActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (citiesList.size()==1 || citiesList.size()==0 ){
//            menu.getItem(1).setEnabled(false);
//        }
        return super.onPrepareOptionsMenu(menu);
    }
    

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PLAY_SERVICES_RESOLUTION_REQUEST:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (checkPlayServices()){
                        buildGoogleApiClient();
                    }
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            switch(requestCode){
                case 1:
                    displayLocation();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registrationBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver,
                new IntentFilter("registrationComplete"));

        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver,
                new IntentFilter(Config.STR_PUSH));

        helper = new PreferencesHelper(this);
        checkPlayServices();
        if (!helper.getFirstTimeLocation()) {

            CitiesDao citiesDao = daoSession.getCitiesDao();
            List<Cities> lst =citiesDao.queryBuilder()
                    .list();
            if (lst.size()==1){
                currentCity.setText(lst.get(0).getCityName());
            }


            citiesList = new SparseArray<>();
            citiesList = fetchLatLonFromDb();
            indicatorView.setCount(citiesList.size());
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), citiesList);
            vpPager.setAdapter(adapterViewPager);
            vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        indicatorView.setSelection(position);
                        adapterPosition = position;
                        CitiesDao citiesDao = daoSession.getCitiesDao();
                        List<Cities> lst = citiesDao.queryBuilder()
                                .list();
                        currentCity.setText(lst.get(position).getCityName());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // build a list of cities latitudes and longitudes
    private SparseArray fetchLatLonFromDb(){

        SparseArray<FirstFragment> fragment = new SparseArray<>();
        CitiesDao citiesDao = daoSession.getCitiesDao();
        List<Cities> lst =citiesDao.queryBuilder()
                .list();

        if(lst.size()>0) {
            for (int i = 0; i < lst.size(); i++) {
                fragment.put(i,FirstFragment.newInstance(lst.get(i).getLatitude(), lst.get(i).getLongitude()));
            }
        }
        return fragment;
    }


    // get current location of user for first time
    private void displayLocation() {

        if(Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        lastLocation = LocationServices.FusedLocationApi
                .getLastLocation(googleApiClient);


        if (lastLocation != null) {
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                String cityName = geocoder.getFromLocation(latitude,longitude,1).get(0).getLocality();
                currentCity.setText(cityName);
                addLatandLonToDb(cityName,latitude,longitude);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            displayPromptForEnablingGPS();
            // Toast.makeText(getActivity(),"(Couldn't get the location. Make sure location is enabled on the device)",Toast.LENGTH_LONG).show();
        }

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), fetchLatLonFromDb());
        vpPager.setAdapter(adapterViewPager);
    }

    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        googleApiClient.connect();
    }

    // check play services is available in user device or not
    private boolean checkPlayServices() {
        int resultCode = GoogleApiAvailability
                .getInstance().isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GoogleApiAvailability.getInstance().isUserResolvableError(resultCode)) {
                GoogleApiAvailability.getInstance().getErrorDialog(this,resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this,
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    public void displayPromptForEnablingGPS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Enable your GPS/Location Service");
        builder.setCancelable(false);
        builder.setPositiveButton("GPS Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(gpsIntent,1);
                dialog.dismiss();
            }});

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"(Couldn't get the location. Make sure location is enabled on the device)",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        builder.show();

    }

    //insert latitude and longitude of the cities into database
    private void addLatandLonToDb(String cityName,double lat,double lon){
        CitiesDao citiesDao = daoSession.getCitiesDao();
        Cities cities = new Cities();

        cities.setCityName(cityName);
        cities.setLatitude(lat);
        cities.setLongitude(lon);

        citiesDao.insert(cities);
    }



}
