package com.in.apnisevatechinican.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apnisevatechinican.adapter.TimeAdapter;
import com.in.apnisevatechinican.modelclass.Availability_ModelClass;
import com.in.apnisevatechinican.R;

import java.util.ArrayList;

public class Availability extends Fragment {

    RecyclerView recyclerSunday,recyclerMonday,recyclerTuesday,
            recyclerWednesday,recyclerThuresday,recyclerFriday,recyclerSaterday;

    TimeAdapter timeAdapter;
    GridLayoutManager gridLayoutManager;
    ArrayList<Availability_ModelClass> availability = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.availability,container,false);

        recyclerSunday = view.findViewById(R.id.recyclerSunday);
        recyclerMonday = view.findViewById(R.id.recyclerMonday);
        recyclerTuesday = view.findViewById(R.id.recyclerTuesday);
        recyclerWednesday = view.findViewById(R.id.recyclerWednesday);
        recyclerThuresday = view.findViewById(R.id.recyclerThuresday);
        recyclerFriday = view.findViewById(R.id.recyclerFriday);
        recyclerSaterday = view.findViewById(R.id.recyclerSaterday);

        availability.add(new Availability_ModelClass("12AM - 2AM"));
        availability.add(new Availability_ModelClass("2AM - 4AM"));
        availability.add(new Availability_ModelClass("4AM - 6AM"));
        availability.add(new Availability_ModelClass("6AM - 8AM"));
        availability.add(new Availability_ModelClass("8AM - 10AM"));
        availability.add(new Availability_ModelClass("10AM - 12PM"));
        availability.add(new Availability_ModelClass("12PM - 2PM"));
        availability.add(new Availability_ModelClass("2PM - 4PM"));
        availability.add(new Availability_ModelClass("4PM - 6PM"));
        availability.add(new Availability_ModelClass("6PM - 8PM"));
        availability.add(new Availability_ModelClass("8PM - 10PM"));
        availability.add(new Availability_ModelClass("10PM - 12AM"));

        timeAdapter = new TimeAdapter(availability,getContext());

        GridLayoutManager gridLayoutManagerSunday = new GridLayoutManager(getContext(),5,GridLayoutManager.VERTICAL,false);
        recyclerSunday.setLayoutManager(gridLayoutManagerSunday);
        recyclerSunday.setHasFixedSize(true);
        recyclerSunday.setAdapter(timeAdapter);

        GridLayoutManager gridLayoutManagerMonday = new GridLayoutManager(getContext(),5,GridLayoutManager.VERTICAL,false);
        recyclerMonday.setLayoutManager(gridLayoutManagerMonday);
        recyclerMonday.setHasFixedSize(true);
        recyclerMonday.setAdapter(timeAdapter);

        GridLayoutManager gridLayoutManagerTuesday = new GridLayoutManager(getContext(),5,GridLayoutManager.VERTICAL,false);
        recyclerTuesday.setLayoutManager(gridLayoutManagerTuesday);
        recyclerTuesday.setHasFixedSize(true);
        recyclerTuesday.setAdapter(timeAdapter);

        GridLayoutManager gridLayoutManagerWednesday = new GridLayoutManager(getContext(),5,GridLayoutManager.VERTICAL,false);
        recyclerWednesday.setLayoutManager(gridLayoutManagerWednesday);
        recyclerWednesday.setHasFixedSize(true);
        recyclerWednesday.setAdapter(timeAdapter);

        GridLayoutManager gridLayoutManagerThuresday = new GridLayoutManager(getContext(),5,GridLayoutManager.VERTICAL,false);
        recyclerThuresday.setLayoutManager(gridLayoutManagerThuresday);
        recyclerThuresday.setHasFixedSize(true);
        recyclerThuresday.setAdapter(timeAdapter);

        GridLayoutManager gridLayoutManagerFriday = new GridLayoutManager(getContext(),5,GridLayoutManager.VERTICAL,false);
        recyclerFriday.setLayoutManager(gridLayoutManagerFriday);
        recyclerFriday.setHasFixedSize(true);
        recyclerFriday.setAdapter(timeAdapter);

        GridLayoutManager gridLayoutManagerSaterday = new GridLayoutManager(getContext(),5,GridLayoutManager.VERTICAL,false);
        recyclerSaterday.setLayoutManager(gridLayoutManagerSaterday);
        recyclerSaterday.setHasFixedSize(true);
        recyclerSaterday.setAdapter(timeAdapter);



        return view;
    }
}
