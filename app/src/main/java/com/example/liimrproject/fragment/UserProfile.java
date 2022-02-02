package com.example.liimrproject.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.liimrproject.R;

public class UserProfile extends Fragment {

    TextView text_edit;
    EditText edit_name,edit_Email,edit_MobileNo,edit_Password;
    Button btn_AvaiLabiLity;
    boolean passwordVisiable;

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
        btn_AvaiLabiLity = view.findViewById(R.id.btn_AvaiLabiLity);


        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();

            }
        });
        btn_AvaiLabiLity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Availability availability = new Availability();
                ft.replace(R.id.fram, availability);
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


        return view;
    }
}
