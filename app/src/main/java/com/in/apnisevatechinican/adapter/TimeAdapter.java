package com.in.apnisevatechinican.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apnisevatechinican.R;
import com.in.apnisevatechinican.modelclass.Availability_ModelClass;

import java.util.ArrayList;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    ArrayList<Availability_ModelClass> time;
    Context context;

    public TimeAdapter(ArrayList<Availability_ModelClass> availability, Context context) {

        this.time = availability;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aviliabilitycheck,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TimeAdapter.ViewHolder holder, int position) {

        Availability_ModelClass available = time.get(position);

        holder.text_ShowTime.setText(available.getTime());

        if(available.getTime().equals("4AM - 6AM")){
            holder.rel_color.setBackgroundColor(Color.parseColor("#9E9A9A"));
        }else if(available.getTime().equals("2AM - 4AM")){
            holder.rel_color.setBackgroundColor(Color.parseColor("#9E9A9A"));
        }else if(available.getTime().equals("10AM - 12PM")){
            holder.rel_color.setBackgroundColor(Color.parseColor("#9E9A9A"));
        }else if(available.getTime().equals("8AM - 10AM")){
            holder.rel_color.setBackgroundColor(Color.parseColor("#9E9A9A"));
        }else if(available.getTime().equals("10AM - 12PM")){
            holder.rel_color.setBackgroundColor(Color.parseColor("#9E9A9A"));
        }else if(available.getTime().equals("4PM - 6PM")){
            holder.rel_color.setBackgroundColor(Color.parseColor("#9E9A9A"));
        }

    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ShowTime;
        RelativeLayout rel_color;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_ShowTime = itemView.findViewById(R.id.text_ShowTime);
            rel_color = itemView.findViewById(R.id.rel_color);
        }
    }
}
