package com.example.admin.weatherui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.weatherui.R;
import com.example.admin.weatherui.model.Hours;
import com.example.admin.weatherui.model.HoursDatum;
import com.example.admin.weatherui.utils.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HoursVH> {

    private Context context;
    private List<HoursDatum> hours;
    private Hours hour;

    public HoursAdapter(Context mContext, List<HoursDatum> mHours,Hours hour) {
        this.context = mContext;
        this.hours = mHours;
        this.hour =hour;
    }


    @Override
    public HoursVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_row,parent,false);
        return new HoursVH(view);
    }


    @Override
    public void onBindViewHolder(HoursVH holder, int position) {

        String timeZone =hour.getTimezone();
        String[] tZone = timeZone.split("\\\\");
        String newTimeZone= tZone[0];
        holder.hours.setText(Utils.timeStampConverter(hours.get(position).getTs(),newTimeZone));
        holder.temp.setText(String.valueOf(Math.round(hours.get(position).getTemp())+"°"));

        String url = Utils.urlForIcon(hours.get(position).getWeather().getIcon());

        int icon = Utils.iconId(hours.get(position).getWeather().getIcon());

        Picasso.with(context)
                .load(icon)
                .into(holder.cloud);

    }

    @Override
    public int getItemCount() {
        return hours.size();
    }




    public class HoursVH extends RecyclerView.ViewHolder{

        TextView hours,temp;
        ImageView cloud;

        public HoursVH(View itemView) {
            super(itemView);

            hours =(TextView)itemView.findViewById(R.id.txtHour);
            temp = (TextView) itemView.findViewById(R.id.txtHoursTemp);
            cloud =(ImageView) itemView.findViewById(R.id.imgHourCloud);
        }
    }
}
