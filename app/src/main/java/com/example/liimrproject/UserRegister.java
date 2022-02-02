package com.example.liimrproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class UserRegister extends AppCompatActivity {

    Button btn_signup;
    TextView text_SignIn;
    Spinner WorkingCategory,WorkingSubcategory,Workingcity;
    String[] Working_city = { "Working_city","Bhubanesware", "Cuttack", "Puri"};
    String[] Working_Subcategory = { "Working_Subcategory","Ac", "Tv", "Others"};
    String[] Working_Category = { "Working_Category","ElectriCian", "Plumbing", "CCTVB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uaer_register);

        btn_signup = findViewById(R.id.btn_signup);
        text_SignIn = findViewById(R.id.text_SignIn);
        WorkingCategory = findViewById(R.id.WorkingCategory);
        WorkingSubcategory = findViewById(R.id.WorkingSubcategory);
        Workingcity = findViewById(R.id.Workingcity);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter WorkingCategory_adapter = new ArrayAdapter(this,R.layout.spinneritem,Working_Category);
        WorkingCategory_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        WorkingSubcategory.setAdapter(WorkingCategory_adapter);
        WorkingSubcategory.setSelection(-1,true);

        ArrayAdapter Working_Subcategory_adapter = new ArrayAdapter(this,R.layout.spinneritem,Working_Subcategory);
        Working_Subcategory_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        WorkingCategory.setAdapter(Working_Subcategory_adapter);
        WorkingCategory.setSelection(-1,true);

        ArrayAdapter Working_city_adapter = new ArrayAdapter(this,R.layout.spinneritem,Working_city);
        Working_city_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        Workingcity.setAdapter(Working_city_adapter);
        Workingcity.setSelection(-1,true);



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegister.this,MainActivity.class);
                startActivity(intent);
            }
        });

        text_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegister.this,UserLogin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}