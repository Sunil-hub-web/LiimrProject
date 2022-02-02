package com.example.liimrproject.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liimrproject.MainActivity;
import com.example.liimrproject.R;
import com.example.liimrproject.fragment.JobDetails;
import com.example.liimrproject.modelclass.MyJobDetails_ModelClass;

import java.util.ArrayList;

public class MyJobDetailsAdapter extends RecyclerView.Adapter<MyJobDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<MyJobDetails_ModelClass> myjob;

    public MyJobDetailsAdapter(FragmentActivity activity, ArrayList<MyJobDetails_ModelClass> myJobDetails) {

        this.context = activity;
        this.myjob = myJobDetails;
    }

    @NonNull
    @Override
    public MyJobDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myjobdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyJobDetailsAdapter.ViewHolder holder, int position) {

        MyJobDetails_ModelClass myjob_details = myjob.get(position);

        holder.booking_Id.setText(myjob_details.getBookingId());
        holder.category_Name.setText("Maintenance");
        holder.customer_Name.setText(myjob_details.getCoustomer_Name());

        if(myjob_details.getBook_pay_status().equals("online")){
            holder.status.setText("Paid");
        }else if(myjob_details.getBook_pay_status().equals("cash")){
            holder.status.setText("Awaiting Payment");
        }

        holder.image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                JobDetails jobDetails = new JobDetails();
                MainActivity.text_name.setVisibility(View.GONE);
                MainActivity.bookingId.setText(myjob_details.getBookingId());
                MainActivity.linearLayout.setVisibility(View.VISIBLE);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fram, jobDetails).addToBackStack(null).commit();

                SharedPreferences sp_jobs = context.getSharedPreferences("details",context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp_jobs.edit();
                editor.putString("id",myjob_details.getBookingId());
                editor.putString("name",myjob_details.getCoustomer_Name());
                editor.putString("address",myjob_details.getAddress());
                editor.putString("address1",myjob_details.getAddress1());
                editor.putString("Book_pay_status",myjob_details.getBook_pay_status());
                editor.putString("Pin_verification_status",myjob_details.getPin_verification_status());
                editor.putString("Price",myjob_details.getPrice());
                editor.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return myjob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView booking_Id,category_Name,customer_Name,status;
        ImageView image_click;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            booking_Id = itemView.findViewById(R.id.booking_Id);
            category_Name = itemView.findViewById(R.id.category_Name);
            customer_Name = itemView.findViewById(R.id.customer_Name);
            image_click = itemView.findViewById(R.id.image_click);
        }
    }
}
