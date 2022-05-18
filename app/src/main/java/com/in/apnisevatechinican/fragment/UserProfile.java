package com.in.apnisevatechinican.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.in.apnisevatechinican.Extra.AppUrl;
import com.in.apnisevatechinican.Extra.VolleyMultipartRequest;
import com.in.apnisevatechinican.R;
import com.in.apnisevatechinican.SharedPrefManager;
import com.in.apnisevatechinican.UserLogin;
import com.in.apnisevatechinican.UserRegister;
import com.in.apnisevatechinican.adapter.CategorySpinerAdapter;
import com.in.apnisevatechinican.modelclass.CategoryDetails_model;
import com.in.apnisevatechinican.modelclass.Login_ModelClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends Fragment {

    TextView text_edit;
    EditText edit_name,edit_Email,edit_MobileNo,edit_Password;
    Button btn_AvaiLabiLity,image_change,btn_Updateaddress,btn_UpdateServices;
    boolean passwordVisiable;
    public static final int IMAGE_CODE = 1;
    Uri imageUri, selectedImageUri;
    private Bitmap bitmap;
    File f;
    String ImageDecode,userid,profileimage,str_name,str_Email,str_MobileNo,str_Password,str_category,str_Subcategory,str_City,str_CityId;
    CircleImageView profile_image;
    private static final int REQUEST_PERMISSIONS = 100;
    AwesomeValidation awesomeValidation;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.userprofile,container,false);

        text_edit = view.findViewById(R.id.text_edit);
        edit_name = view.findViewById(R.id.edit_name);
        edit_Email = view.findViewById(R.id.edit_Email);
        edit_MobileNo = view.findViewById(R.id.edit_MobileNo);
        edit_Password = view.findViewById(R.id.edit_Password);
        //btn_AvaiLabiLity = view.findViewById(R.id.btn_AvaiLabiLity);
        profile_image = view.findViewById(R.id.profile_image);
        image_change = view.findViewById(R.id.image_change);
        btn_Updateaddress = view.findViewById(R.id.btn_Updateaddress);
        //userId = view.findViewById(R.id.userId);
        btn_UpdateServices = view.findViewById(R.id.btn_UpdateServices);

        userid = SharedPrefManager.getInstance(getActivity()).getUser().getUserid();
        profileimage = SharedPrefManager.getInstance(getActivity()).getUser().getImage();
        str_Password = SharedPrefManager.getInstance(getActivity()).getUser().getPassword();
        str_category = SharedPrefManager.getInstance(getActivity()).getUser().getCategory();
        str_Subcategory = SharedPrefManager.getInstance(getActivity()).getUser().getSubcategory();
        str_City = SharedPrefManager.getInstance(getActivity()).getUser().getCity();
        str_CityId = SharedPrefManager.getInstance(getActivity()).getUser().getCity_id();

        Log.d("profileimagesunil",profileimage);

        getUserProfile(userid);

        Picasso.with(getContext()).load(profileimage).placeholder(R.drawable.no_avatar).into(profile_image);


        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit_name.setEnabled(true);
                edit_Email.setEnabled(true);
                edit_MobileNo.setEnabled(true);

                edit_name.setFocusable(true);
                edit_name.requestFocus();

                text_edit.setTextColor(ContextCompat.getColor(getContext(),R.color.back1));

            }
        });

        btn_Updateaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_name.getText().toString().equals("")){

                    edit_name.setError("Fill The Details");

                }else if(!isValidUserName(edit_name.getText().toString())){

                    edit_name.setError("Fill The Details");

                }else  if(edit_Email.getText().toString().equals("")){

                    edit_Email.setError("Fill The Details");

                }else if(!isValidEmail(edit_Email.getText().toString())){

                    edit_Email.setError("Fill The Details");

                }else if(edit_MobileNo.getText().toString().equals("")){

                    edit_MobileNo.setError("Fill The Details");

                }else if(edit_MobileNo.getText().toString().trim().length()!=10){

                    edit_MobileNo.setError("Please Provide 10 digit Mobile No");

                }else if(edit_Password.getText().toString().equals("")){

                    edit_Password.setError("Fill The Details");

                }else{

                    str_name = edit_name.getText().toString().trim();
                    str_Email = edit_Email.getText().toString().trim();
                    str_MobileNo = edit_MobileNo.getText().toString().trim();
                    //str_Password = edit_Password.getText().toString().trim();

                    updateprofile(userid,str_name,str_Email,str_MobileNo,str_CityId);
                }

            }
        });

      /*
        btn_AvaiLabiLity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Availability availability = new Availability();
                ft.replace(R.id.fram, availability);
                ft.addToBackStack(null);
                ft.commit();
            }
        });*/

        btn_UpdateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                UpdateServices updateServices = new UpdateServices();
                ft.replace(R.id.fram, updateServices);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


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
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_lock,0,R.drawable.baseline_visibility_off,0);
                            // for show Password
                            edit_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisiable = false;
                            edit_Password.setCompoundDrawablePadding(15);

                        }else{

                            //set Drawable Image here
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_lock,0,R.drawable.baseline_visibility,0);
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

        image_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");

                    showFileChooser();
                }

            }
        });


        return view;
    }

    public void getUserProfile(String userid){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("User Login Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.technician_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equalsIgnoreCase("OK")){

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String mobile = jsonObject.getString("mobile");
                        String image = jsonObject.getString("image");
                        String category = jsonObject.getString("category");
                        String subcategory = jsonObject.getString("subcategory");
                        String city = jsonObject.getString("city");

                        edit_name.setText(name);
                        edit_MobileNo.setText(mobile);
                        edit_Email.setText(email);
                        edit_Password.setText(str_Password);
                        //userId.setText(id);

                        Picasso.with(getContext()).load(image).placeholder(R.drawable.no_avatar).into(profile_image);

                        edit_name.setEnabled(false);
                        edit_Email.setEnabled(false);
                        edit_MobileNo.setEnabled(false);
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
                params.put("id",userid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void updateprofile(String id,String name,String email,String mobileNo,String city){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Update Profile Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.updateprofile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                        String data = jsonObject.getString("data");

                        JSONObject jsonObject_data = new JSONObject(data);

                        String name = jsonObject_data.getString("name");
                        String id = jsonObject_data.getString("id");
                        String email = jsonObject_data.getString("email");
                        String mobile = jsonObject_data.getString("mobile");
                        String email_verified_at = jsonObject_data.getString("email_verified_at");
                        String profile_img = jsonObject_data.getString("profile_img");
                        String city = jsonObject_data.getString("city");
                        String pass = edit_Password.getText().toString().trim();

                        edit_name.setText(name);
                        edit_MobileNo.setText(mobile);
                        edit_Email.setText(email);
                        edit_Password.setText(str_Password);
                        //userId.setText(id);

                        text_edit.setTextColor(ContextCompat.getColor(getContext(), R.color.textcol));

                        edit_name.setEnabled(false);
                        edit_Email.setEnabled(false);
                        edit_MobileNo.setEnabled(false);

                        Login_ModelClass login_modelClass = new Login_ModelClass(
                                id,mobile,email,name,pass,profile_img,str_category,str_Subcategory,str_City,str_CityId
                        );

                        SharedPrefManager.getInstance(getContext()).userLogin(login_modelClass);

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
                Toast.makeText(getActivity(), "API not response, Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id",id);
                params.put("name",name);
                params.put("email",email);
                params.put("mobile",mobileNo);
                params.put("city",city);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void showFileChooser() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "title"), IMAGE_CODE);

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK &&
                    data != null && data.getData() != null) {

                imageUri = data.getData();
                //profile_image.setImageURI(imageUri);

                String[] FILE = {MediaStore.Images.Media.DATA};


                Cursor cursor = getActivity().getContentResolver().query(imageUri,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                f = new File(ImageDecode);
                selectedImageUri = Uri.fromFile(f);
                Log.d("selectedImageUri", selectedImageUri.toString());
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                profile_image.setImageBitmap(bitmap);
                //profile_image.setImageURI(selectedImageUri);

                Log.d("ImageDecode", ImageDecode);
                Log.d("ImageDecode1", f.toString());
                Log.d("ImageDecode2", selectedImageUri.toString());

                cursor.close();

                if (selectedImageUri.equals("")) {

                    Toast.makeText(getContext(), "Select Image", Toast.LENGTH_SHORT).show();

                } else {

                    uploadProfileimage(userid,bitmap);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void uploadProfileimage(String userid, Bitmap bitmap){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Image is being uploaded Please wait...");
        progressDialog.show();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppUrl.updateprofileimage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));

                    String status = jsonObject.getString("status");

                    if (status.equals("ok")) {

                        String message = jsonObject.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        //viewProfileDetails();

                    } else {

                        String message = jsonObject.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        //viewProfileDetails();
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
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", userid);
                params.put("user_id", userid);

                return params;
            }

            @Override
            protected Map<String,DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;


            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(volleyMultipartRequest);

    }

    public void getCity(){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
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

                            //Working_city.add(categoryDetails_model);

                        }

                        //CategorySpinerAdapter Working_city_adapter = new CategorySpinerAdapter(getActivity(),R.layout.spinneritem,Working_city);
                        //Working_city_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        //Workingcity.setAdapter(Working_city_adapter);
                        //Workingcity.setSelection(-1,true);
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
                Toast.makeText(getActivity(), "error"+error, Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public boolean isValidUserName(final String userName) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN =  "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";

        pattern =  Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (userName);

        return matcher.matches ( );

    }

    public boolean isValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;

        //final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        pattern = Patterns.EMAIL_ADDRESS;
        matcher = pattern.matcher(email);

        return matcher.matches();

        //return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
