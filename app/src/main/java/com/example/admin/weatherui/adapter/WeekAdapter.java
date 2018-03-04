package com.example.admin.weatherui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.weatherui.R;
import com.example.admin.weatherui.model.WeekDatum;
import com.example.admin.weatherui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekVH> {

    private Context context;
    private List<WeekDatum> weekData;

    public WeekAdapter(Context mContext, List<WeekDatum> mWeekData) {
        this.context = mContext;
        this.weekData = mWeekData;
    }


    @Override
    public WeekVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder","executed!");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_row,parent,false);
        return new WeekVH(view);
    }


    @Override
    public void onBindViewHolder(WeekVH holder, int position) {
        Log.d("onBindViewHolder","executed!");
        holder.dayOfWeek.setText(Utils.convertDateToDay(weekData.get(position).getDatetime()));
        holder.minTemp.setText(String.valueOf(Math.round(weekData.get(position).getMin_temp()))+"°");
        holder.maxTemp.setText(String.valueOf(Math.round(weekData.get(position).getMax_temp()))+"°");

        String url = Utils.urlForIcon(weekData.get(position).getWeather().getIcon());
        int icon = Utils.iconId(weekData.get(position).getWeather().getIcon());

        Picasso.with(context)
                .load(icon)
                .into(holder.daysIcon);

    }



    @Override
    public int getItemCount() {
        Log.d("getItemCount","executed!");
        return weekData.size();
    }






    public class WeekVH extends RecyclerView.ViewHolder{

        TextView dayOfWeek,minTemp,maxTemp;
        ImageView daysIcon;

        public WeekVH(View itemView) {
            super(itemView);

            dayOfWeek =(TextView)itemView.findViewById(R.id.daysOfWeek);
            minTemp = (TextView)itemView.findViewById(R.id.minTemp);
            maxTemp = (TextView)itemView.findViewById(R.id.maxTemp);
            daysIcon = (ImageView)itemView.findViewById(R.id.daysIcon);
        }
    }
}
