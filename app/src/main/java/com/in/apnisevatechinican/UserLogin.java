package com.in.apnisevatechinican;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apnisevatechinican.Extra.AppUrl;
import com.in.apnisevatechinican.R;
import com.in.apnisevatechinican.modelclass.Login_ModelClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLogin extends AppCompatActivity {

    Button btn_signin;
    TextView text_signUp,text_ForgotPassword;
    EditText edit_MobileNumber,edit_Password;
    String str_MobileNumber,str_Password;
    boolean passwordVisiable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        btn_signin = findViewById(R.id.btn_signin);
        text_signUp = findViewById(R.id.text_signUp);
        edit_MobileNumber = findViewById(R.id.edit_MobileNumber);
        edit_Password = findViewById(R.id.edit_Password);
        text_ForgotPassword = findViewById(R.id.text_ForgotPassword);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_Password.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int Right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP){

                    if(event.getRawX() >= edit_Password.getRight() - edit_Password.getCompoundDrawables()[Right].getBounds().width()){

                        int selection = edit_Password.getSelectionEnd();
                        if(passwordVisiable){

                            //set Drawable Image here
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off,0);
                            // for show Password
                            edit_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisiable = false;
                            edit_Password.setCompoundDrawablePadding(15);

                        }else{

                            //set Drawable Image here
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility,0);
                            // for show Password
                            edit_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisiable = true;
                            edit_Password.setCompoundDrawablePadding(15);
                        }

                        edit_Password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_MobileNumber.getText().toString().trim().equals("")){

                    edit_MobileNumber.setError("Fill the mobile no");

                }else if(edit_Password.getText().toString().trim().equals("")){

                    edit_Password.setError("Fill the password");

                }else{

                    str_MobileNumber = edit_MobileNumber.getText().toString().trim();
                    str_Password = edit_Password.getText().toString().trim();

                    userLogin(str_MobileNumber,str_Password);
                }

            }
        });

        text_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserLogin.this,UserRegister.class);
                startActivity(intent);
            }
        });

        text_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserLogin.this,ForGotPassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    public void userLogin(String key,String password){

        ProgressDialog progressDialog = new ProgressDialog(UserLogin.this);
        progressDialog.setMessage("User Login Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.partnerUserLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equalsIgnoreCase("OK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(UserLogin.this, msg, Toast.LENGTH_LONG).show();

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String mobile = jsonObject.getString("mobile");
                        String image = jsonObject.getString("image");
                        String category = jsonObject.getString("category");
                        String subcategory = jsonObject.getString("subcategory");

                        Login_ModelClass login_modelClass = new Login_ModelClass(
                                id,mobile,email,name,password,image,category,subcategory
                                );

                        SharedPrefManager.getInstance(UserLogin.this).userLogin(login_modelClass);

                        Intent intent = new Intent(UserLogin.this,MainActivity.class);
                        startActivity(intent);

                    }else if(status.equals("NOK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(UserLogin.this, msg, Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.d("userexception",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(UserLogin.this, "Not Update", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("key",key);
                params.put("password",password);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserLogin.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(UserLogin.this).isLoggedIn()){

            Intent intent = new Intent(UserLogin.this,MainActivity.class);
            startActivity(intent);

        }
    }
}