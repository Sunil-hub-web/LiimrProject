package com.example.liimrproject.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.liimrproject.Extra.AppUrl;
import com.example.liimrproject.MainActivity;
import com.example.liimrproject.R;
import com.example.liimrproject.adapter.MyJobDetailsAdapter;
import com.example.liimrproject.modelclass.MyJobDetails_ModelClass;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.myjobs, container, false);

        recyclerJobDetails = view.findViewById(R.id.recyclerJobDetails);

        MainActivity.text_name.setVisibility(View.VISIBLE);
        MainActivity.linearLayout.setVisibility(View.GONE);

        myJobsDetails("63");

        return view;
    }

    public void myJobsDetails(String userId) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrive MyJobs Details Please wait...");
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

                            MyJobDetails_ModelClass myJobDetails_modelClass = new MyJobDetails_ModelClass(
                                    id, id, "", name, book_pay_status, price, mobile, address, address1, subtotal, igst, cgst,
                                    create_user_id, verification_pin, pin_verification_status, work_status, work_details,
                                    extra_amt, payment_status, book_pay_status, online_book_pay_id, pay_type
                            );

                            myJobDetails.add(myJobDetails_modelClass);
                        }

                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        myJobDetailsAdapter = new MyJobDetailsAdapter(getActivity(), myJobDetails);
                        recyclerJobDetails.setLayoutManager(linearLayoutManager);
                        recyclerJobDetails.setHasFixedSize(true);
                        recyclerJobDetails.setAdapter(myJobDetailsAdapter);


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
                params.put("Id", userId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }
}
