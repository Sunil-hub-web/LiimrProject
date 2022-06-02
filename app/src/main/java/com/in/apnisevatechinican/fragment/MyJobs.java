package com.in.apnisevatechinican.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apnisevatechinican.Extra.AppUrl;
import com.in.apnisevatechinican.MainActivity;
import com.in.apnisevatechinican.SharedPrefManager;
import com.in.apnisevatechinican.adapter.MyJobDetailsAdapter;
import com.in.apnisevatechinican.modelclass.MyJobDetails_ModelClass;
import com.in.apnisevatechinican.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyJobs extends Fragment {

    RecyclerView recyclerJobDetails;
    LinearLayoutManager linearLayoutManager;
    ArrayList<MyJobDetails_ModelClass> myJobDetails = new ArrayList<>();
    MyJobDetailsAdapter myJobDetailsAdapter;

    String userId,category;
    LinearLayout cartempty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.myjobs, container, false);

        recyclerJobDetails = view.findViewById(R.id.recyclerJobDetails);
        cartempty = view.findViewById(R.id.cartempty);

        MainActivity.text_name.setVisibility(View.VISIBLE);
        MainActivity.linearLayout.setVisibility(View.GONE);

        userId = SharedPrefManager.getInstance(getActivity()).getUser().getUserid();
        category = SharedPrefManager.getInstance(getActivity()).getUser().getCategory();

        myJobsDetails(userId);

        return view;
    }

    public void myJobsDetails(String userId) {

        myJobDetails.clear();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.myJobsDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String jobs = jsonObject.getString("jobs");

                    if (status.equals("OK")) {

                        JSONArray jsonArray_jobs = new JSONArray(jobs);

                        if(jsonArray_jobs.length() == 0){

                            cartempty.setVisibility(View.VISIBLE);
                            recyclerJobDetails.setVisibility(View.INVISIBLE);

                        }else{

                            for (int i = 0; i < jsonArray_jobs.length(); i++) {

                                JSONObject jsonObject_jobs = jsonArray_jobs.getJSONObject(i);


                                String id = jsonObject_jobs.getString("id");
                                String price = jsonObject_jobs.getString("price");
                                String name = jsonObject_jobs.getString("name");
                                String mobile = jsonObject_jobs.getString("mobile");
                                String address = jsonObject_jobs.getString("address");
                                String address1 = jsonObject_jobs.getString("address1");
                                String subtotal = jsonObject_jobs.getString("subtotal");
                                String igst = jsonObject_jobs.getString("igst");
                                String cgst = jsonObject_jobs.getString("cgst");
                                String create_user_id = jsonObject_jobs.getString("create_user_id");
                                String verification_pin = jsonObject_jobs.getString("verification_pin");
                                String pin_verification_status = jsonObject_jobs.getString("pin_verification_status");
                                String work_status = jsonObject_jobs.getString("work_status");
                                String work_details = jsonObject_jobs.getString("work_details");
                                String extra_amt = jsonObject_jobs.getString("extra_amt");
                                String payment_status = jsonObject_jobs.getString("payment_status");
                                String book_pay_status = jsonObject_jobs.getString("book_pay_status");
                                String online_book_pay_id = jsonObject_jobs.getString("online_book_pay_id");
                                String pay_type = jsonObject_jobs.getString("pay_type");
                                String created_at = jsonObject_jobs.getString("created_at");

                                MyJobDetails_ModelClass myJobDetails_modelClass = new MyJobDetails_ModelClass(
                                        id, id, category, name, book_pay_status, price, mobile, address, address1, subtotal, igst, cgst,
                                        create_user_id, verification_pin, pin_verification_status, work_status, work_details,
                                        extra_amt, payment_status, book_pay_status, online_book_pay_id, pay_type,created_at
                                );

                                myJobDetails.add(myJobDetails_modelClass);
                            }

                            cartempty.setVisibility(View.INVISIBLE);
                            recyclerJobDetails.setVisibility(View.VISIBLE);
                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);
                            myJobDetailsAdapter = new MyJobDetailsAdapter(getActivity(), myJobDetails);
                            recyclerJobDetails.setLayoutManager(linearLayoutManager);
                            recyclerJobDetails.setHasFixedSize(true);
                            recyclerJobDetails.setAdapter(myJobDetailsAdapter);

                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();

                Toast.makeText(getActivity(), "Pin Not verify", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", userId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }
}
