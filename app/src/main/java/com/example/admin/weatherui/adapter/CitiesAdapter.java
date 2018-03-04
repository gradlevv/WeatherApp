package com.example.admin.weatherui.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.admin.weatherui.R;
import com.example.admin.weatherui.api.WeatherApiService;
import com.example.admin.weatherui.db.Cities;

import com.example.admin.weatherui.db.CitiesDao;
import com.example.admin.weatherui.db.DaoSession;
import com.example.admin.weatherui.model.Current;
import com.example.admin.weatherui.utils.AppController;
import com.example.admin.weatherui.utils.Utils;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitiesAdapter extends RecyclerSwipeAdapter<CitiesAdapter.CitiesVH> {

    private DaoSession daoSession;
    private Context context;

    private List<Cities> citiesList;

    public CitiesAdapter(Context context,Activity activity,List<Cities> cities) {
        daoSession = ((AppController)activity.getApplication()).getDaoSession();
        this.citiesList =cities;
        this.context= context;
    }

    @Override
    public CitiesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_row,parent,false);
        return new CitiesVH(view);
    }

    @Override
    public void onBindViewHolder(final CitiesVH holder, int position) {

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (citiesList.size()==1){
                    Toast.makeText(context,"City can not delete !",Toast.LENGTH_LONG).show();
                }else {
                    mItemManger.removeShownLayouts(holder.swipeLayout);
                    Cities cities = citiesList.get(holder.getAdapterPosition());
                    citiesList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), citiesList.size());
                    mItemManger.closeAllItems();

                    DeleteQuery<Cities> citiesDeleteQuery = daoSession.queryBuilder(Cities.class)
                            .whereOr(CitiesDao.Properties.Latitude.eq(cities.getLatitude()), CitiesDao.Properties.Longitude.eq(cities.getLongitude()),
                                    CitiesDao.Properties.CityName.eq(cities.getCityName()))
                            .buildDelete();
                    citiesDeleteQuery.executeDeleteWithoutDetachingEntities();
                    daoSession.clear();
                }

            }
        });


        holder.name.setText(citiesList.get(holder.getAdapterPosition()).getCityName());

        Call<Current> call = WeatherApiService.getCurrent().getCurrentWeather(citiesList.get(holder.getAdapterPosition()).getLatitude(), citiesList.get(holder.getAdapterPosition()).getLongitude(), context.getString(R.string.api_key));
        call.enqueue(new Callback<Current>() {
                @Override
                public void onResponse(@NonNull Call<Current> call, @NonNull Response<Current> response) {
                    if (response.isSuccessful()) {
                        holder.deg.setText(String.valueOf(Math.round(response.body().getData().get(0).getTemp())));
                        String timeZone = response.body().getData().get(0).getTimezone();
                        String[] tZone = timeZone.split("\\\\");
                        String newTimeZone = tZone[0];
                        holder.time.setText(Utils.currentTime(newTimeZone));
                        String tz = Utils.currentTime(newTimeZone);
                        if (Utils.parseDate(tz).equals("day")){
                            holder.relativeLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.clear_day_bg));
                        }else if (Utils.parseDate(tz).equals("night")){
                            holder.relativeLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_partly_cloudy_night2_min));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Current> call, @NonNull Throwable t) {
                    Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();
                    Log.d("current", t.getMessage());
                }
            });


        mItemManger.bindView(holder.itemView, holder.getAdapterPosition());

    }


    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }



    public class CitiesVH extends RecyclerView.ViewHolder{

        TextView time,name,deg;
        SwipeLayout swipeLayout;
        Button delete;
        RelativeLayout relativeLayout;

        public CitiesVH(View itemView) {
            super(itemView);
            time = (TextView)itemView.findViewById(R.id.citiesTime);
            name = (TextView)itemView.findViewById(R.id.citiesName);
            deg = (TextView)itemView.findViewById(R.id.citiesDeg);
            delete = (Button)itemView.findViewById(R.id.delete_button);
            relativeLayout =(RelativeLayout)itemView.findViewById(R.id.view_foreground);
            swipeLayout= (SwipeLayout)itemView.findViewById(R.id.swipe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"delete select",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
