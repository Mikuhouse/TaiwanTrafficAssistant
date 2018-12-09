package com.example.taiwantrafficassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.taiwantrafficassistant.bus.BusListActivity;
import com.example.taiwantrafficassistant.test.TestGithubRepoApiActivity;
import com.example.taiwantrafficassistant.test.TestPtxApiActivity;

public class MainActivity extends AppCompatActivity {
    //TODO 建立GridView並點擊導向功能列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //retrieve xml layout component
        TextView bus = (TextView) findViewById(R.id.tv_bus);
        TextView test = (TextView) findViewById(R.id.tv_test) ;

        //set textview on click action
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent busListIntent = new Intent(MainActivity.this, BusListActivity.class);
                startActivity(busListIntent);
            }

        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MainActivity.this, TestGithubRepoApiActivity.class);
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
