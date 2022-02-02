package com.example.liimrproject.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.liimrproject.Extra.AppUrl;
import com.example.liimrproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateStatus extends Fragment {

    CardView finalWorkDetails,pinVerifay,card_Payment,card_AllocatedTechician,card_WorkInfo;
    RelativeLayout rel_Pinverified,rel_WorkCompleted,rel_PaymentTobe;
    Button btn_CashApply,btn_CashPaidAopply,btn_PinVerifyApply;
    EditText edit_VerifythePin,edit_CompleteAddress;
    RadioButton radio_RecievedCash;
    TextView text,edit_Money,text_price,priceAmount,ShowPrice;
    String Pin_verification_status,Book_pay_status,Price;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.updatestatus,container,false);


        pinVerifay = view.findViewById(R.id.pinVerifay);
        ShowPrice = view.findViewById(R.id.ShowPrice);
        finalWorkDetails = view.findViewById(R.id.finalWorkDetails);
        card_Payment = view.findViewById(R.id.card_Payment);
        card_AllocatedTechician = view.findViewById(R.id.card_AllocatedTechician);
        card_WorkInfo = view.findViewById(R.id.card_WorkInfo);
        rel_Pinverified = view.findViewById(R.id.rel_Pinverified);
        rel_WorkCompleted = view.findViewById(R.id.rel_WorkCompleted);
        rel_PaymentTobe = view.findViewById(R.id.rel_PaymentTobe);
        btn_PinVerifyApply = view.findViewById(R.id.btn_PinVerifyApply);
        btn_CashPaidAopply = view.findViewById(R.id.btn_CashPaidAopply);
        btn_CashApply = view.findViewById(R.id.btn_CashApply);
        edit_VerifythePin = view.findViewById(R.id.edit_VerifythePin);
        radio_RecievedCash = view.findViewById(R.id.radio_RecievedCash);
        text = view.findViewById(R.id.text);
        priceAmount = view.findViewById(R.id.priceAmount);
        edit_Money = view.findViewById(R.id.edit_Money);
        text_price = view.findViewById(R.id.text_price);
        edit_CompleteAddress = view.findViewById(R.id.edit_CompleteAddress);

        rel_Pinverified.setVisibility(View.GONE);
        rel_WorkCompleted.setVisibility(View.GONE);
        rel_PaymentTobe.setVisibility(View.GONE);
        radio_RecievedCash.setVisibility(View.GONE);
        btn_CashApply.setVisibility(View.GONE);

        //Retrieve the value
        Pin_verification_status = getArguments().getString("Pin_verification_status");
        Book_pay_status = getArguments().getString("Book_pay_status");
        Price = getArguments().getString("Price");

        if(Pin_verification_status.equals("0")){

            rel_Pinverified.setVisibility(View.GONE);



        }else{

            rel_Pinverified.setVisibility(View.VISIBLE);

        }

        if(Book_pay_status.equals("online")){

            rel_PaymentTobe.setVisibility(View.VISIBLE);

            rel_PaymentTobe.setBackgroundResource(R.drawable.updateconfrom_bg);

            priceAmount.setVisibility(View.GONE);
            text.setText("Paid");
            text.setTextColor(Color.WHITE);
            radio_RecievedCash.setVisibility(View.GONE);
            btn_CashApply.setVisibility(View.GONE);

            edit_Money.setText(Price);
            text_price.setText("0");

            ShowPrice.setText("Work Completed");

            card_Payment.setClickable(false);

        }else{

            edit_Money.setText(Price);
            text_price.setText(Price);

            finalWorkDetails.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

            priceAmount.setText(Price);

            card_Payment.setClickable(true);

        }

        card_AllocatedTechician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pinVerifay.getVisibility() == View.GONE){

                    if(rel_Pinverified.getVisibility() == View.VISIBLE){

                        pinVerifay.setVisibility(View.GONE);

                    }else{

                        pinVerifay.setVisibility(View.VISIBLE);
                    }

                    //rel_Pinverified.setVisibility(View.VISIBLE);

                }else{

                    pinVerifay.setVisibility(View.GONE);

                }

            }
        });

        btn_PinVerifyApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edit_VerifythePin.getText().toString().trim().equals("")){

                    edit_VerifythePin.setError("Enter Your Pin");

                }else {

                    String str_VerifythePin = edit_VerifythePin.getText().toString().trim();

                    verifypinPincode("101",str_VerifythePin);

                    if(pinVerifay.getVisibility() == View.VISIBLE){

                        pinVerifay.setVisibility(View.GONE);
                        rel_Pinverified.setVisibility(View.VISIBLE);

                        //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        card_WorkInfo.setClickable(true);


                    }

                }

            }
        });

        card_WorkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rel_Pinverified.getVisibility() == View.VISIBLE){

                    if(Pin_verification_status.equals("0")){

                        if(finalWorkDetails.getVisibility() == View.VISIBLE){

                            finalWorkDetails.setVisibility(View.GONE);
                            //rel_Pinverified.setVisibility(View.VISIBLE);

                        }else{

                            finalWorkDetails.setVisibility(View.VISIBLE);
                        }

                    }else{

                        rel_WorkCompleted.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                        rel_PaymentTobe.setVisibility(View.VISIBLE);

                    }

                }else{

                    finalWorkDetails.setVisibility(View.GONE);
                }


              /*  if(Pin_verification_status.equals("0")){

                    if(finalWorkDetails.getVisibility() == View.VISIBLE){

                        finalWorkDetails.setVisibility(View.GONE);
                        //rel_Pinverified.setVisibility(View.VISIBLE);

                    }else{

                        finalWorkDetails.setVisibility(View.VISIBLE);
                    }

                }else{

                    rel_WorkCompleted.setVisibility(View.VISIBLE);

                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                    rel_PaymentTobe.setVisibility(View.VISIBLE);

                }*/



            }
        });

        btn_CashPaidAopply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_CompleteAddress.getText().toString().trim().equals("")){

                    edit_CompleteAddress.setError("Fill the work details");
                    edit_CompleteAddress.setFocusable(true);

                }else {

                    String str_CompleteAddress = edit_CompleteAddress.getText().toString().trim();

                    workinfo("101",Price,str_CompleteAddress);

                    if(finalWorkDetails.getVisibility() == View.VISIBLE) {

                        if (text_price.getText().toString().trim().equals("0")) {

                            rel_PaymentTobe.setVisibility(View.VISIBLE);

                            rel_WorkCompleted.setVisibility(View.VISIBLE);

                            rel_PaymentTobe.setBackgroundResource(R.drawable.updateconfrom_bg);

                            finalWorkDetails.setVisibility(View.GONE);
                            priceAmount.setVisibility(View.GONE);
                            text.setText("Paid");
                            text.setTextColor(Color.WHITE);
                            radio_RecievedCash.setVisibility(View.GONE);
                            btn_CashApply.setVisibility(View.GONE);

                            edit_Money.setText(Price);
                            text_price.setText("0");
                            ShowPrice.setText("Work Completed");

                            card_Payment.setClickable(false);

                        } else {

                            finalWorkDetails.setVisibility(View.GONE);
                            rel_WorkCompleted.setVisibility(View.VISIBLE);
                            radio_RecievedCash.setVisibility(View.VISIBLE);
                            btn_CashApply.setVisibility(View.VISIBLE);

                            ///Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                            rel_PaymentTobe.setVisibility(View.VISIBLE);
                            priceAmount.setText(Price);

                            card_Payment.setClickable(true);

                            ShowPrice.setText("Work Completed & Rs" +" " + Price +" " +"is due");

                        }
                    }

                }
            }
        });

        btn_CashApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radio_RecievedCash.isChecked()){

                    paymentcash("101","cash");

                }else{

                    Toast.makeText(getActivity(), "Check radio button", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public void workinfo(String id,String amount,String workDetails){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Update WorkInfo Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.workInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    if(status.equals("ok")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


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

                Toast.makeText(getActivity(), "Not Update", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Id",id);
                params.put("amount",amount);
                params.put("work_detail",workDetails);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void paymentcash(String id,String pay_type){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Working Payment Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.paymentType, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    if(status.equals("ok")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        rel_PaymentTobe.setVisibility(View.VISIBLE);

                        rel_PaymentTobe.setBackgroundResource(R.drawable.updateconfrom_bg);

                        priceAmount.setVisibility(View.GONE);
                        text.setText("Paid");
                        text.setTextColor(Color.WHITE);
                        radio_RecievedCash.setVisibility(View.GONE);
                        btn_CashApply.setVisibility(View.GONE);

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

                Toast.makeText(getActivity(), "Payment Not Done", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Id",id);
                params.put("pay_type",pay_type);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void verifypinPincode(String id,String pin){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Pin Verify Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.verifypinPin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("ok")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    }else if(status.equals("Nok")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Id",id);
                params.put("pin",pin);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}
