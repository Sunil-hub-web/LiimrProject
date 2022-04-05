package com.in.apnisevatechinican;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apnisevatechinican.Extra.AppUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class VerificationCode extends AppCompatActivity {

    TextView text_Timer,resend_OTP,resendtext,mobileNumber;
    Button btn_verifayOtp;
    String userOTP,userMobileNo,className,str_MobileNo;
    OtpTextView otp_view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        Window window = VerificationCode.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(VerificationCode.this, R.color.white));

        text_Timer = findViewById(R.id.timer);
        resend_OTP = findViewById(R.id.resend_OTP);
        btn_verifayOtp = findViewById(R.id.btn_verifayOtp);
        resendtext = findViewById(R.id.resendtext);
        otp_view = findViewById(R.id.otp_view);
        mobileNumber = findViewById(R.id.mobileNumber);

        className = getIntent().getStringExtra("className");
        str_MobileNo = getIntent().getStringExtra("mobileno");

        timer();

            mobileNumber.setText("+91 " +str_MobileNo);


        //otp_view.setOTP(userOTP);

        resend_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_Timer.setVisibility(View.VISIBLE);
                resendtext.setVisibility(View.VISIBLE);
                resend_OTP.setVisibility(View.GONE);

                timer();

                resendotp(userMobileNo,"send_otp");
            }
        });

        btn_verifayOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otp_view.getOTP().length() != 4){

                    Toast.makeText(VerificationCode.this, "Enter Valide Otp", Toast.LENGTH_SHORT).show();

                }else{


                        String getOTP = otp_view.getOTP();
                        verifayOtpPass(str_MobileNo,getOTP,"verify_otp");

                }

            }
        });

    }

    public void timer(){

        //Initialize time duration
        long duration = TimeUnit.MINUTES.toMillis(1);
        //Initialize countdown timer

        new CountDownTimer(duration, 5) {
            @Override
            public void onTick(long millisUntilFinished) {

                //When tick
                //Convert millisecond to minute and second

                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                text_Timer.setText(sDuration);

            }

            @Override
            public void onFinish() {

                text_Timer.setVisibility(View.GONE);
                resendtext.setVisibility(View.GONE);
                resend_OTP.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void resendotp(String mobileNo,String type){

        ProgressDialog progressDialog = new ProgressDialog(VerificationCode.this);
        progressDialog.setMessage("OTP Send mobile No Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.partnerForgotPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(VerificationCode.this, msg, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(VerificationCode.this,VerificationCode.class);
                        intent.putExtra("className","ForGotPassword");
                        intent.putExtra("mobileno",mobileNo);
                        startActivity(intent);

                    }else if(status.equalsIgnoreCase("NOK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(VerificationCode.this, msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("successresponceVolley", "" + error.networkResponse);
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            String jError = new String(networkResponse.data);
                            JSONObject jsonError = new JSONObject(jError);
//                            if (error.networkResponse.statusCode == 400) {
                            String data = jsonError.getString("message");
                            Toast.makeText(VerificationCode.this, data, Toast.LENGTH_SHORT).show();

//                            } else if (error.networkResponse.statusCode == 404) {
//                                JSONArray data = jsonError.getJSONArray("msg");
//                                JSONObject jsonitemChild = data.getJSONObject(0);
//                                String ms = jsonitemChild.toString();
//                                Toast.makeText(RegisterActivity.this, ms, Toast.LENGTH_SHORT).show();
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("successresponceVolley", "" + e);
                        }
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("mobile",mobileNo);
                params.put("type",type);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(VerificationCode.this);
        requestQueue.add(stringRequest);
    }

    public void verifayOtpPass(String mobileNo,String OTP,String type){

        ProgressDialog progressDialog = new ProgressDialog(VerificationCode.this);
        progressDialog.setMessage("Verification Your Otp Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.partnerForgotPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.d("Ranjeet_verfyOtp",response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("status");

                    if(message.equals("OK")){

                        String message1 = jsonObject.getString("msg");

                        Toast.makeText(VerificationCode.this, message1, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(VerificationCode.this,ForgotPasswordActivity.class);
                        intent.putExtra("mobileNo",mobileNo);
                        startActivity(intent);

                    }else if(message.equals("NOK")){

                        String message1 = jsonObject.getString("msg");
                        Toast.makeText(VerificationCode.this, message1, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(VerificationCode.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("type",type);
                params.put("mobile",mobileNo);
                params.put("otp",OTP);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(VerificationCode.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}