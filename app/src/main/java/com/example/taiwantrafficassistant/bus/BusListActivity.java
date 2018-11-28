package com.example.taiwantrafficassistant.bus;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.taiwantrafficassistant.MainActivity;
import com.example.taiwantrafficassistant.R;

public class BusListActivity extends AppCompatActivity {
    //TODO 建立公車功能列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        // retrieve xml layout
        ConstraintLayout busButoton = (ConstraintLayout) findViewById(R.id.cl_route_search);
        busButoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent( BusListActivity.this, BusRouteSearchActivity.class);
                startActivity(numbersIntent);
            }
        });
    }


}
