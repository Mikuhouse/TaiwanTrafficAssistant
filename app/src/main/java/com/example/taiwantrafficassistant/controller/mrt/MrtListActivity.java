package com.example.taiwantrafficassistant.controller.mrt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.taiwantrafficassistant.R;

public class MrtListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrt_list);
        setTitle("捷運功能列表");
    }
}
