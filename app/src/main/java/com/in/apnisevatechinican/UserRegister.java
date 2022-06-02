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
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.in.apnisevatechinican.adapter.CategorySpinerAdapter;
import com.in.apnisevatechinican.modelclass.CategoryDetails_model;
import com.in.apnisevatechinican.Extra.AppUrl;
import com.in.apnisevatechinican.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRegister extends AppCompatActivity {

    Button btn_signup;
    TextView text_SignIn,text_TermsService;
    boolean passwordVisiable;
    AwesomeValidation awesomeValidation;
    Spinner WorkingCategory,WorkingSubcategory,Workingcity;
    ArrayList<CategoryDetails_model> Working_city = new ArrayList<>();
    ArrayList<CategoryDetails_model> Working_Category = new ArrayList<>();
    ArrayList<CategoryDetails_model> Working_Subcategory = new ArrayList<>();
    String city_Id,city_Name,category_Id,category_Name,subcategory_Id,subcategory_Name,str_fullname,
            str_MobileNumber,str_Email,str_Password,str_ConfirmPassword;
    EditText edit_fullname,edit_MobileNumber,edit_Email,edit_Password,edit_ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uaer_register);

        btn_signup = findViewById(R.id.btn_signup);
        text_SignIn = findViewById(R.id.text_SignIn);
        WorkingCategory = findViewById(R.id.WorkingCategory);
        WorkingSubcategory = findViewById(R.id.WorkingSubcategory);
        Workingcity = findViewById(R.id.Workingcity);
        edit_Password = findViewById(R.id.edit_Password);
        edit_fullname = findViewById(R.id.edit_fullname);
        edit_MobileNumber = findViewById(R.id.edit_MobileNumber);
        edit_Email = findViewById(R.id.edit_Email);
        edit_ConfirmPassword = findViewById(R.id.edit_ConfirmPassword);
        text_TermsService = findViewById(R.id.text_TermsService);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(UserRegister.this, R.id.edit_fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.entername);
        awesomeValidation.addValidation(UserRegister.this, R.id.edit_MobileNumber, "^[+]?[0-9]{10}$", R.string.entercontact);
        awesomeValidation.addValidation(UserRegister.this, R.id.edit_Email, Patterns.EMAIL_ADDRESS, R.string.enteremail);

        getCity();
        getCategory();


        Workingcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    CategoryDetails_model cate_data = (CategoryDetails_model) parent.getSelectedItem();

                    city_Id = cate_data.getId();
                    city_Name = cate_data.getName();
                    Log.d("city_Id", city_Id);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        WorkingCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    CategoryDetails_model cate_data = (CategoryDetails_model) parent.getSelectedItem();

                    category_Id = cate_data.getId();
                    category_Name = cate_data.getName();
                    Log.d("category_Id", category_Id);

                    getSubCategory(category_Id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        WorkingSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    CategoryDetails_model cate_data = (CategoryDetails_model) parent.getSelectedItem();

                    subcategory_Id = cate_data.getId();
                    subcategory_Name = cate_data.getName();
                    Log.d("subcategory_Id", subcategory_Id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit_Password.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(edit_Password.getText().toString().trim().equals("edit_ConfirmPassword")){

                    edit_Password.setError("Fill Details");

                }else{

                    final int Right = 2;
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        if (event.getRawX() >= edit_Password.getRight() - edit_Password.getCompoundDrawables()[Right].getBounds().width()) {

                            int selection = edit_Password.getSelectionEnd();
                            if (passwordVisiable) {

                                //set Drawable Image here
                                edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off, 0);
                                // for show Password
                                edit_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                passwordVisiable = false;

                            } else {

                                //set Drawable Image here
                                edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility, 0);
                                // for show Password
                                edit_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                passwordVisiable = true;
                            }

                            edit_Password.setSelection(selection);
                            return true;
                        }
                    }
                }

                return false;
            }
        });

        edit_ConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(edit_ConfirmPassword.getText().toString().trim().equals("edit_ConfirmPassword")){

                    edit_ConfirmPassword.setError("Fill Details");

                }else{

                    final int Right = 2;
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        if (event.getRawX() >= edit_ConfirmPassword.getRight() - edit_ConfirmPassword.getCompoundDrawables()[Right].getBounds().width()) {

                            int selection = edit_ConfirmPassword.getSelectionEnd();
                            if (passwordVisiable) {

                                //set Drawable Image here
                                edit_ConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off, 0);
                                // for show Password
                                edit_ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                passwordVisiable = false;

                            } else {

                                //set Drawable Image here
                                edit_ConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility, 0);
                                // for show Password
                                edit_ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                passwordVisiable = true;
                            }

                            edit_ConfirmPassword.setSelection(selection);
                            return true;
                        }
                    }
                }

                return false;
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate()){

                    if(edit_Password.getText().toString().trim().equalsIgnoreCase(edit_ConfirmPassword.getText().toString().trim())){

                        str_fullname = edit_fullname.getText().toString().trim();
                        str_MobileNumber = edit_MobileNumber.getText().toString().trim();
                        str_Email = edit_Email.getText().toString().trim();
                        str_Password = edit_Password.getText().toString().trim();
                        str_ConfirmPassword = edit_ConfirmPassword.getText().toString().trim();

                        userRegister(str_fullname,str_Email,str_MobileNumber,city_Id,category_Id,subcategory_Id,str_Password);

                    }else{

                        Toast.makeText(UserRegister.this, "Password Not Match", Toast.LENGTH_SHORT).show();

                        edit_ConfirmPassword.setError("Password Not Match");

                    }

                }else{

                    Toast.makeText(UserRegister.this, "Plese enter validate data", Toast.LENGTH_SHORT).show();

                }

            }
        });

        text_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegister.this,UserLogin.class);
                startActivity(intent);
            }
        });

        text_TermsService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegister.this,TermsofService.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    public void getCity(){

        ProgressDialog progressDialog = new ProgressDialog(UserRegister.this);
        progressDialog.setMessage("City Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrl.getmastercity, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String city = jsonObject.getString("city");
                        JSONArray jsonArray_city = new JSONArray(city);

                        for(int i=0; i<jsonArray_city.length(); i++){

                            JSONObject jsonObject_City = jsonArray_city.getJSONObject(i);

                            String id = jsonObject_City.getString("id");
                            String city_name = jsonObject_City.getString("city_name");

                            CategoryDetails_model categoryDetails_model = new CategoryDetails_model(

                                    city_name,id
                            );

                            Working_city.add(categoryDetails_model);

                        }

                        CategorySpinerAdapter Working_city_adapter = new CategorySpinerAdapter(UserRegister.this,R.layout.spinneritem,Working_city);
                        Working_city_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        Workingcity.setAdapter(Working_city_adapter);
                        Workingcity.setSelection(-1,true);
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
                Toast.makeText(UserRegister.this, "API no response, Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegister.this);
        requestQueue.add(stringRequest);

    }

    public void getCategory(){

        ProgressDialog progressDialog = new ProgressDialog(UserRegister.this);
        progressDialog.setMessage("Category Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrl.getmastercategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String category = jsonObject.getString("category");
                        JSONArray jsonArray_category = new JSONArray(category);

                        for(int i=0; i<jsonArray_category.length(); i++){

                            JSONObject jsonObject_category = jsonArray_category.getJSONObject(i);

                            String id = jsonObject_category.getString("id");
                            String category_name = jsonObject_category.getString("category_name");

                            CategoryDetails_model categoryDetails_model = new CategoryDetails_model(

                                    category_name,id
                            );

                            Working_Category.add(categoryDetails_model);

                        }

                        CategorySpinerAdapter Working_Subcategory_adapter = new CategorySpinerAdapter(UserRegister.this,R.layout.spinneritem,Working_Category);
                        Working_Subcategory_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        WorkingCategory.setAdapter(Working_Subcategory_adapter);
                        WorkingCategory.setSelection(-1,true);
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
                Toast.makeText(UserRegister.this, "API no response, Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegister.this);
        requestQueue.add(stringRequest);

    }

    public void getSubCategory(String categoryId){

        Working_Subcategory.clear();

        ProgressDialog progressDialog = new ProgressDialog(UserRegister.this);
        progressDialog.setMessage("SubCategory Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getcategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String subcategory = jsonObject.getString("result");
                        JSONArray jsonArray_subcategory = new JSONArray(subcategory);

                        for(int i=0; i<jsonArray_subcategory.length(); i++){

                            JSONObject jsonObject_subcategory = jsonArray_subcategory.getJSONObject(i);

                            String id = jsonObject_subcategory.getString("id");
                            String subcategory_name = jsonObject_subcategory.getString("category_name");

                            CategoryDetails_model categoryDetails_model = new CategoryDetails_model(

                                    subcategory_name,id
                            );

                            Working_Subcategory.add(categoryDetails_model);

                        }

                        CategorySpinerAdapter WorkingCategory_adapter = new CategorySpinerAdapter(UserRegister.this,R.layout.spinneritem,Working_Subcategory);
                        WorkingCategory_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        WorkingSubcategory.setAdapter(WorkingCategory_adapter);
                        WorkingSubcategory.setSelection(-1,true);
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
                Toast.makeText(UserRegister.this, "API no response, Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",categoryId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegister.this);
        requestQueue.add(stringRequest);

    }

    public void userRegister(String name,String email,String mobile,String city_id,String cat_id,
                             String subcat_id,String password){

        ProgressDialog progressDialog = new ProgressDialog(UserRegister.this);
        progressDialog.setMessage("User Register Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.partnerRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        Toast.makeText(UserRegister.this, "User Register Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UserRegister.this,UserLogin.class);
                        startActivity(intent);

                    }else if(status.equalsIgnoreCase("NOK")){

                        String message = jsonObject.getString("message");
                        Toast.makeText(UserRegister.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UserRegister.this, "API no response, Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("name",name);
                params.put("email",email);
                params.put("mobile",mobile);
                params.put("city_id",city_id);
                params.put("cat_id",cat_id);
                params.put("subcat_id",subcat_id);
                params.put("password",password);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegister.this);
        requestQueue.add(stringRequest);
    }

}