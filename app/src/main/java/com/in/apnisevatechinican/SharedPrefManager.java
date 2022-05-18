package com.in.apnisevatechinican;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.in.apnisevatechinican.modelclass.Login_ModelClass;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_email = "keylast_name";
    private static final String KEY_mobile_number = "keymobile_number";
    private static final String KEY_ID = "keyid";
    private static final String KEY_password = "keypassword";
    private static final String KEY_Name = "keyname";
    private static final String KEY_IMAGE = "keyimage";
    private static final String KEY_subcategory = "subcategory";
    private static final String KEY_category = "category";
    private static final String KEY_City = "city";
    private static final String KEY_City_ID = "cityid";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user register
    //this method will store the user data in shared preferences
    public void userLogin(Login_ModelClass login_modelClass) {

        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();


        editor.putString(KEY_ID,         login_modelClass.getUserid ());
        editor.putString(KEY_mobile_number,     login_modelClass.getMobileNo ());
        editor.putString(KEY_email,                login_modelClass.getEmailId ());
        editor.putString(KEY_Name,                login_modelClass.getUserName ());
        editor.putString(KEY_password,                login_modelClass.getPassword ());
        editor.putString(KEY_IMAGE,                login_modelClass.getImage ());
        editor.putString(KEY_category,                login_modelClass.getCategory ());
        editor.putString(KEY_subcategory,                login_modelClass.getSubcategory ());
        editor.putString(KEY_City,                login_modelClass.getCity ());
        editor.putString(KEY_City_ID,                login_modelClass.getCity_id ());


        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPrefManager.getString(KEY_mobile_number, null) != null;
    }

    //this method will give the logged in user
    public Login_ModelClass getUser() {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Login_ModelClass(


                sharedPrefManager.getString(KEY_ID, null),
                sharedPrefManager.getString(KEY_mobile_number, null),
                sharedPrefManager.getString(KEY_email, null),
                sharedPrefManager.getString(KEY_Name, null),
                sharedPrefManager.getString(KEY_password, null),
                sharedPrefManager.getString(KEY_IMAGE, null),
                sharedPrefManager.getString(KEY_category, null),
                sharedPrefManager.getString(KEY_subcategory, null),
                sharedPrefManager.getString(KEY_City, null),
                sharedPrefManager.getString(KEY_City_ID, null)

        );

    }

    //this method will logout the user
    public void logout() {

        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent (mCtx, UserLogin.class));
    }

}
