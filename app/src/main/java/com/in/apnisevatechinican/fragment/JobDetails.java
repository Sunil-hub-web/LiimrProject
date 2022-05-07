package com.in.apnisevatechinican.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.in.apnisevatechinican.MainActivity;
import com.in.apnisevatechinican.SharedPrefManager;
import com.in.apnisevatechinican.R;

public class JobDetails extends Fragment {

    TextView update_Status,CompleteAddress,customer_Name,status_name,text_category;
    String Pin_verification_status,Book_pay_status,address1,address,name,id,Price,category,work_status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.jobdetails, container, false);

        update_Status = view.findViewById(R.id.update_Status);
        CompleteAddress = view.findViewById(R.id.CompleteAddress);
        customer_Name = view.findViewById(R.id.customer_Name);
        status_name = view.findViewById(R.id.status_name);
        text_category = view.findViewById(R.id.text_category);

        category = SharedPrefManager.getInstance(getActivity()).getUser().getCategory();

        SharedPreferences sp_jobs = getActivity().getSharedPreferences("details",getActivity().MODE_PRIVATE);
        id = sp_jobs.getString("id",null);
        name = sp_jobs.getString("name",null);
        address = sp_jobs.getString("address",null);
        address1 = sp_jobs.getString("address1",null);
        Book_pay_status = sp_jobs.getString("Book_pay_status",null);
        Pin_verification_status = sp_jobs.getString("Pin_verification_status",null);
        Price = sp_jobs.getString("Price",null);
        work_status = sp_jobs.getString("work_status",null);

        MainActivity.bookingId.setText(id);

        CompleteAddress.setText(address+"\n"+address1);
        customer_Name.setText(name);
        text_category.setText(category);

        if(work_status.equals("payment_done")){
            status_name.setText("payment_done");
        }
        else if(Book_pay_status.equals("online")){
            status_name.setText("Paid");
        }else if(Book_pay_status.equals("cash")){
            status_name.setText("Awaiting Payment");
        }

        update_Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                UpdateStatus updateStatus = new UpdateStatus();
                MainActivity.text_name.setText("My Jobs");
                Bundle args = new Bundle();
                args.putString("Pin_verification_status", Pin_verification_status);
                args.putString("Book_pay_status", Book_pay_status);
                args.putString("Price", Price);
                args.putString("orderid", id);
                args.putString("work_status", work_status);
                updateStatus.setArguments(args);
                ft.replace(R.id.fram, updateStatus);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        return view;
    }
}
