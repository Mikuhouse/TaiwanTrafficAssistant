package com.example.taiwantrafficassistant.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.controller.bus.activity.BusListActivity;
import com.example.taiwantrafficassistant.controller.mrt.MrtMapWebviewActivity;
import com.example.taiwantrafficassistant.test.TestGithubRepoApiActivity;
import com.example.taiwantrafficassistant.test.TestPtxApiActivity;

public class MainActivity extends AppCompatActivity {
    //TODO 建立GridView並點擊導向功能列表
    ImageView mBus;
    ImageView mMrt;
    //RecyclerView mMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ////retrieve xml layout component
        mBus = findViewById(R.id.cl_main_bus);
        mMrt = findViewById(R.id.cl_main_mrt);




        //set textview on click action
        mBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent busListIntent = new Intent(MainActivity.this, BusListActivity.class);
                startActivity(busListIntent);
            }

        });

        mMrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MainActivity.this, MrtMapWebviewActivity.class);
                startActivity(testIntent);
            }

        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_test_github_repo_api) {
            Intent testIntent = new Intent(MainActivity.this, TestGithubRepoApiActivity.class);
            startActivity(testIntent);
            return true;
        }

        if (itemThatWasClickedId == R.id.action_test_ptx_railway_api) {
            Intent testIntent = new Intent(MainActivity.this, TestPtxApiActivity.class);
            startActivity(testIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
