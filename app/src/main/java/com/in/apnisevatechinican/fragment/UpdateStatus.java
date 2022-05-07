package com.in.apnisevatechinican.fragment;

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
import com.in.apnisevatechinican.SharedPrefManager;
import com.in.apnisevatechinican.Extra.AppUrl;
import com.in.apnisevatechinican.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateStatus extends Fragment {

    CardView finalWorkDetails, pinVerifay, card_Payment, card_AllocatedTechician, card_WorkInfo;
    RelativeLayout rel_Pinverified, rel_WorkCompleted, rel_PaymentTobe;
    Button btn_CashApply, btn_CashPaidAopply, btn_PinVerifyApply;
    EditText edit_VerifythePin, edit_CompleteAddress;
    RadioButton radio_RecievedCash;
    TextView text, edit_Money, booking_price, priceAmount, ShowPrice, text_name, text_contact,text_TechicianName;
    String Pin_verification_status, Book_pay_status, Price, user_Id, orderid, str_technicianName, str_TechnicianMobile, str_TechnicianImage, work_status;
    CircleImageView profile_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.updatestatus, container, false);


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
        edit_Money = view.findViewById(R.id.filal_Money);
        booking_price = view.findViewById(R.id.booking_price);
        edit_CompleteAddress = view.findViewById(R.id.edit_CompleteAddress);
        text_name = view.findViewById(R.id.text_name);
        text_contact = view.findViewById(R.id.text_contact);
        profile_image = view.findViewById(R.id.profile_image);
        text_TechicianName = view.findViewById(R.id.text_TechicianName);

        rel_Pinverified.setVisibility(View.GONE);
        rel_WorkCompleted.setVisibility(View.GONE);
        rel_PaymentTobe.setVisibility(View.GONE);
        radio_RecievedCash.setVisibility(View.GONE);
        btn_CashApply.setVisibility(View.GONE);

        //Retrieve the value
        Pin_verification_status = getArguments().getString("Pin_verification_status");
        Book_pay_status = getArguments().getString("Book_pay_status");
        Price = getArguments().getString("Price");
        orderid = getArguments().getString("orderid");
        work_status = getArguments().getString("work_status");

        orderstatues(orderid);

        user_Id = SharedPrefManager.getInstance(getActivity()).getUser().getUserid();

        str_technicianName = SharedPrefManager.getInstance(getActivity()).getUser().getUserName();
        str_TechnicianMobile = SharedPrefManager.getInstance(getActivity()).getUser().getMobileNo();
        str_TechnicianImage = SharedPrefManager.getInstance(getActivity()).getUser().getImage();

        text_name.setText(str_technicianName);
        text_contact.setText(str_TechnicianMobile);
        Picasso.with(getActivity()).load(str_TechnicianImage).placeholder(R.drawable.profileimage).into(profile_image);

        if (Pin_verification_status.equals("0")) {

            rel_Pinverified.setVisibility(View.GONE);

        } else {

            rel_Pinverified.setVisibility(View.VISIBLE);

        }

        if(work_status.equals("allocated")){

            orderstatues(orderid);

        }else if(work_status.equals("progress")){

            rel_Pinverified.setVisibility(View.VISIBLE);
            pinVerifay.setVisibility(View.GONE);

        }else if(work_status.equals("generated")){

            finalWorkDetails.setVisibility(View.GONE);
            rel_WorkCompleted.setVisibility(View.VISIBLE);
            radio_RecievedCash.setVisibility(View.VISIBLE);
            btn_CashApply.setVisibility(View.VISIBLE);

            ///Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

            rel_PaymentTobe.setVisibility(View.VISIBLE);
            priceAmount.setText(Price);

            card_Payment.setClickable(true);

            ShowPrice.setText("Work Completed & Rs" +" " + Price +" " +"is due");

            orderstatues(orderid);

        }else if(work_status.equals("payment_done")){

            rel_WorkCompleted.setVisibility(View.VISIBLE);
            rel_PaymentTobe.setBackgroundResource(R.drawable.updateconfrom_bg);
            rel_PaymentTobe.setVisibility(View.VISIBLE);
            priceAmount.setVisibility(View.GONE);
            text.setText("Paid");
            text.setTextColor(Color.WHITE);
            radio_RecievedCash.setVisibility(View.GONE);
            btn_CashApply.setVisibility(View.GONE);

            orderstatues(orderid);

        } else if (Book_pay_status.equals("online")) {

            rel_PaymentTobe.setVisibility(View.VISIBLE);

            rel_PaymentTobe.setBackgroundResource(R.drawable.updateconfrom_bg);

            priceAmount.setVisibility(View.GONE);
            text.setText("Paid");
            text.setTextColor(Color.WHITE);
            radio_RecievedCash.setVisibility(View.GONE);
            btn_CashApply.setVisibility(View.GONE);

            edit_Money.setText(Price);
            booking_price.setText("0");

            ShowPrice.setText("Work Completed");

            card_Payment.setClickable(false);

        } else {

            edit_Money.setText(Price);
            booking_price.setText(Price);

            finalWorkDetails.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

            priceAmount.setText(Price);

            card_Payment.setClickable(true);

            radio_RecievedCash.setVisibility(View.VISIBLE);
            btn_CashApply.setVisibility(View.VISIBLE);

        }



        card_AllocatedTechician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (work_status.equals("allocated")) {

                    if (pinVerifay.getVisibility() == View.GONE) {

                        if (rel_Pinverified.getVisibility() == View.VISIBLE) {

                            pinVerifay.setVisibility(View.GONE);

                        } else {

                            pinVerifay.setVisibility(View.VISIBLE);
                        }

                    } else {

                        pinVerifay.setVisibility(View.GONE);

                    }
                }
            }
        });

        btn_PinVerifyApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_VerifythePin.getText().toString().trim().equals("")) {

                    edit_VerifythePin.setError("Enter The Pin");

                } else {

                    String str_VerifythePin = edit_VerifythePin.getText().toString().trim();
                    verifypinPincode(orderid, str_VerifythePin);

                    orderstatues(orderid);
                }
            }
        });

        card_WorkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (work_status.equals("progress")) {

                    if (rel_WorkCompleted.getVisibility() == View.VISIBLE) {

                        finalWorkDetails.setVisibility(View.GONE);

                    } else {

                        if (rel_Pinverified.getVisibility() == View.VISIBLE) {

                            if (finalWorkDetails.getVisibility() == View.VISIBLE) {

                                finalWorkDetails.setVisibility(View.GONE);
                                //rel_Pinverified.setVisibility(View.VISIBLE);

                            } else {

                                finalWorkDetails.setVisibility(View.VISIBLE);
                            }
                        } else {

                            Toast.makeText(getActivity(), "Please verified the Pin", Toast.LENGTH_SHORT).show();

                            finalWorkDetails.setVisibility(View.GONE);
                            rel_PaymentTobe.setVisibility(View.GONE);
                            rel_WorkCompleted.setVisibility(View.GONE);
                        }
                    }
                } else {

                    if (rel_WorkCompleted.getVisibility() == View.VISIBLE) {

                        finalWorkDetails.setVisibility(View.GONE);

                    } else {

                        if (rel_Pinverified.getVisibility() == View.VISIBLE) {

                            if (finalWorkDetails.getVisibility() == View.VISIBLE) {

                                finalWorkDetails.setVisibility(View.GONE);
                                //rel_Pinverified.setVisibility(View.VISIBLE);

                            } else {

                                finalWorkDetails.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                }
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

                    workinfo(orderid,Price,str_CompleteAddress);

                    if(finalWorkDetails.getVisibility() == View.VISIBLE) {

                        if (booking_price.getText().toString().trim().equals("0")) {

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
                            booking_price.setText("0");
                            ShowPrice.setText("Work Completed");

                            card_Payment.setClickable(false);

                            orderstatues(orderid);

                        } else {

                            finalWorkDetails.setVisibility(View.GONE);
                            rel_WorkCompleted.setVisibility(View.VISIBLE);
                            radio_RecievedCash.setVisibility(View.VISIBLE);
                            btn_CashApply.setVisibility(View.VISIBLE);

                            ///Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                            rel_PaymentTobe.setVisibility(View.VISIBLE);
                            priceAmount.setText(Price);

                            String money = edit_Money.getText().toString().trim();
                            double price = Double.valueOf(money);
                            double price1 = Double.valueOf(money);
                            double totalprice = price+price1;
                            String str_total = String.valueOf(totalprice);

                            card_Payment.setClickable(true);

                            ShowPrice.setText("Work Completed & Rs" +" " + str_total +" " +"is due");

                            orderstatues(orderid);

                        }
                    }

                }
            }
        });

        btn_CashApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radio_RecievedCash.isChecked()){

                    paymentcash(orderid,"cash");

                }else{

                    Toast.makeText(getActivity(), "Check radio button", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void workinfo(String orderid, String amount, String workDetails) {

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
                    if (status.equals("ok")) {

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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", orderid);
                params.put("amount", amount);
                params.put("work_detail", workDetails);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void paymentcash(String orderid, String pay_type) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Working Payment Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.paymentType, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if (status.equals("ok")) {

                        String message = jsonObject.getString("message");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        rel_PaymentTobe.setVisibility(View.VISIBLE);

                        rel_PaymentTobe.setBackgroundResource(R.drawable.updateconfrom_bg);

                        priceAmount.setVisibility(View.GONE);
                        text.setText("Paid");
                        ShowPrice.setText("Work Completed");
                        text.setTextColor(Color.WHITE);
                        radio_RecievedCash.setVisibility(View.GONE);
                        btn_CashApply.setVisibility(View.GONE);

                        orderstatues(orderid);


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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", orderid);
                params.put("pay_type", pay_type);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void verifypinPincode(String orderid, String pin) {

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

                    if (status.equals("ok")) {

                        String message = jsonObject.getString("message");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        if (pinVerifay.getVisibility() == View.VISIBLE) {

                            pinVerifay.setVisibility(View.GONE);
                            rel_Pinverified.setVisibility(View.VISIBLE);

                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            card_WorkInfo.setClickable(true);

                            orderstatues(orderid);
                        }

                    } else if (status.equals("Nok")) {

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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", orderid);
                params.put("pin", pin);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    public void orderstatues(String orderId) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Update Statues Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.orderstatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if (status.equals("ok")) {

                        String bookingdetails = jsonObject.getString("bookingdetails");
                        String technician = jsonObject.getString("technician");

                        JSONObject jsonObject_booking = new JSONObject(bookingdetails);
                        JSONObject jsonObject_technician = new JSONObject(technician);


                        orderid = jsonObject_booking.getString("id");
                        Price = jsonObject_booking.getString("price");
                        Pin_verification_status = jsonObject_booking.getString("pin_verification_status");
                        Book_pay_status = jsonObject_booking.getString("book_pay_status");
                        work_status = jsonObject_booking.getString("work_status");

                        str_technicianName = jsonObject_technician.getString("name");
                        str_TechnicianMobile = jsonObject_technician.getString("mobile");
                        str_TechnicianImage = jsonObject_technician.getString("profile_img");

                        if(str_technicianName != null){

                            text_TechicianName.setText(str_technicianName);
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
                params.put("id", orderId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}
