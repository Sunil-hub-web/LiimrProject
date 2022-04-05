package com.in.apnisevatechinican;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apnisevatechinican.Extra.AppUrl;
import com.in.apnisevatechinican.Extra.CheckInternetConnection;
import com.in.apnisevatechinican.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash_screen extends AppCompatActivity {

    Handler handler;
    ImageView splashScreen;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        splashScreen = findViewById(R.id.splashScreen);

        Window window = Splash_screen.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Splash_screen.this, R.color.white));


        checkInternet();


    }

    public void checkInternet() {

        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Splash_screen.this, CheckInternetConnection.class);
            startActivity(intent);

        } else {

            //Toast.makeText(SplashScreen.this, "Connected", Toast.LENGTH_SHORT).show();
            getsplashscreenImage();

        }
    }

    public void timer() {

        handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Splash_screen.this, UserLogin.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }

    public void getsplashscreenImage() {

        ProgressDialog progressDialog = new ProgressDialog(Splash_screen.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrl.splashScreen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("status");

                    if (message.equals("OK")) {

                        String image = jsonObject.getString("image");

                        Picasso.with(Splash_screen.this).load(image)
                                .into(splashScreen);
                        timer();

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
                Toast.makeText(Splash_screen.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Splash_screen.this);
        requestQueue.add(stringRequest);


    }


}

