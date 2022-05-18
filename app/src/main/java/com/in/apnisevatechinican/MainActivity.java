package com.in.apnisevatechinican;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.in.apnisevatechinican.fragment.MyMessages;
import com.in.apnisevatechinican.fragment.UserProfile;
import com.in.apnisevatechinican.R;
import com.in.apnisevatechinican.fragment.HomePage;
import com.in.apnisevatechinican.fragment.MyJobs;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mydrawer;
    NavigationView navigationView;
    TextView nav_Home,nav_Profile,nav_Message,nav_MyJobs,nav_Availability,nav_Aboutas,nav_logout;
    public static TextView text_name,bookingId;
    public static LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydrawer = (DrawerLayout) findViewById(R.id.mydrwaer);
        navigationView = findViewById(R.id.navigationview);
        text_name = findViewById(R.id.text_name);
        bookingId = findViewById(R.id.bookingId);
        linearLayout = findViewById(R.id.linearLayout);

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        nav_Message = header.findViewById(R.id.nav_Message);
        nav_Profile = header.findViewById(R.id.nav_Profile);
        nav_Home = header.findViewById(R.id.nav_Home);
        nav_MyJobs = header.findViewById(R.id.nav_MyJobs);
        //nav_Availability = header.findViewById(R.id.nav_Availability);
        nav_logout = header.findViewById(R.id.nav_logout);
        //nav_Aboutas = header.findViewById(R.id.nav_Aboutas);



        text_name.setText("Home Page");
        mydrawer.closeDrawer(GravityCompat.START);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomePage home = new HomePage();
        ft.replace(R.id.fram, home,"testID");
        ft.addToBackStack(null);
        ft.commit();
        text_name.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        String mobile = SharedPrefManager.getInstance(MainActivity.this).getUser().getMobileNo();
        String password = SharedPrefManager.getInstance(MainActivity.this).getUser().getPassword();

        Log.d("userdetails",mobile +"  "+password);

        nav_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("My Message");
                mydrawer.closeDrawer(GravityCompat.START);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                MyMessages myMessages = new MyMessages();
                ft.replace(R.id.fram, myMessages);
                ft.addToBackStack(null);
                ft.commit();
                text_name.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });

        nav_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("My Profile");
                mydrawer.closeDrawer(GravityCompat.START);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                UserProfile userProfile = new UserProfile();
                ft.replace(R.id.fram, userProfile);
                ft.addToBackStack(null);
                ft.commit();

                text_name.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });

        nav_MyJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("Job Details");
                mydrawer.closeDrawer(GravityCompat.START);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                MyJobs myJobs = new MyJobs();
                ft.replace(R.id.fram, myJobs);
                ft.addToBackStack(null);
                ft.commit();

                text_name.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);

            }
        });

        nav_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                text_name.setText("Home Page");
                mydrawer.closeDrawer(GravityCompat.START);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                HomePage home = new HomePage();
                ft.replace(R.id.fram, home,"testID");
                ft.addToBackStack(null);
                ft.commit();

                text_name.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });

       /* nav_Availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("Availability");
                mydrawer.closeDrawer(GravityCompat.START);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Availability availability = new Availability();
                ft.replace(R.id.fram, availability);
                ft.addToBackStack(null);
                ft.commit();

                text_name.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });*/

        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(MainActivity.this).logout();
            }
        });

       /* nav_Aboutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("About As");
                mydrawer.closeDrawer(GravityCompat.START);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                AboutAsPage aboutAsPage = new AboutAsPage();
                ft.replace(R.id.fram, aboutAsPage);
                ft.commit();

            }
        });*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mydrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void Clickmenu(View view){

        // open drawer
        openDrawer(mydrawer);
    }

    private static void openDrawer(DrawerLayout drawerLayout){

        // opendrawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

}