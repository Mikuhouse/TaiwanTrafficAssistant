package com.example.taiwantrafficassistant.controller.mrt;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.taiwantrafficassistant.R;

public class MrtListActivity extends AppCompatActivity {
    ConstraintLayout routeSearch;
    ConstraintLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrt_list);
        setTitle("捷運功能列表");
        map = findViewById(R.id.cl_mrt_route_map);
        routeSearch = findViewById(R.id.cl_route_search);
        routeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MrtListActivity.this, MrtMapWebviewActivity.class);
                startActivity(testIntent);
            }

        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MrtListActivity.this, MrtNearbyActivity.class);
                startActivity(testIntent);
            }

        });
    }
}