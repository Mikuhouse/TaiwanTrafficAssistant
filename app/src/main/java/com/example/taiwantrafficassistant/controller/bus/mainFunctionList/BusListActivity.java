package com.example.taiwantrafficassistant.controller.bus.mainFunctionList;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.controller.bus.activity.BusFavorateActivity;
import com.example.taiwantrafficassistant.controller.bus.NearbyStop.BusNearbyStopOnMapActivity;
import com.example.taiwantrafficassistant.controller.bus.routeSearch.BusRouteSearchActivity;

public class BusListActivity extends AppCompatActivity {
    //TODO 建立公車功能列表
    ConstraintLayout busButoton;
    ConstraintLayout favorateButton;
    ConstraintLayout mapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        // retrieve xml layout
        busButoton = findViewById(R.id.cl_route_search);
        favorateButton = findViewById(R.id.cl_favorate);
        mapButton = findViewById(R.id.cl_route_map);

        busButoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent( BusListActivity.this, BusRouteSearchActivity.class);
                startActivity(numbersIntent);
            }
        });
        favorateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent( BusListActivity.this, BusFavorateActivity.class);
                startActivity(numbersIntent);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent( BusListActivity.this, BusNearbyStopOnMapActivity.class);
                startActivity(numbersIntent);
            }
        });
    }


}
