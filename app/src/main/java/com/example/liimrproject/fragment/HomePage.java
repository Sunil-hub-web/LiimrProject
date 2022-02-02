package com.example.liimrproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.liimrproject.MainActivity;
import com.example.liimrproject.R;

public class HomePage extends Fragment {

    Button btn_MyJob;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.maindeshbord, container, false);

        btn_MyJob = view.findViewById(R.id.btn_MyJob);

        MainActivity.text_name.setVisibility(View.VISIBLE);
        MainActivity.linearLayout.setVisibility(View.GONE);

        btn_MyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                MyJobs myJobs = new MyJobs();
                MainActivity.text_name.setText("My Jobs");
                ft.replace(R.id.fram, myJobs);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }
}
