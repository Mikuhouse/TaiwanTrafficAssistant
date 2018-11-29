package com.example.taiwantrafficassistant.test;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taiwantrafficassistant.ptx.SignatureTest;

import com.example.taiwantrafficassistant.R;

import java.io.IOException;
import java.net.URL;

public class TestPtxRailwayApiActivity extends AppCompatActivity {
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ptx_railway_api);
        result = findViewById(R.id.tv_railway_result);

        new QueryTask().execute("123");
    }

    public class QueryTask extends AsyncTask<String , Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... x) {
            //Toast.makeText(TestPtxRailwayApiActivity.this, "do", Toast.LENGTH_LONG);
            return SignatureTest.test();
        }

        @Override
        protected void onPostExecute(String results) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            //Toast.makeText(TestPtxRailwayApiActivity.this, "complete", Toast.LENGTH_LONG);
            result.setText(results);
        }
    }
}
