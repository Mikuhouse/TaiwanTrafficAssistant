package com.example.taiwantrafficassistant.bus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.utilities.BusRouteJsonUtils;
import com.example.taiwantrafficassistant.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class BusTimeOfArrivalActivity extends AppCompatActivity {

    TextView mDisplayText;
    String routeNameToSearch = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time_of_arrival);
        mDisplayText= findViewById(R.id.tv_ptx_test_arrival_time_result);

        //取回intent的EXTRA_TEXT
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            routeNameToSearch = textEntered;
            setTitle(routeNameToSearch);
            showBusBusTimeOfArrival();
        }else{
            setTitle("Null");
        }

    }

    /**
            取得到站時間並顯示在畫面上
     */
    private void showBusBusTimeOfArrival(){
        new QueryTask().execute(routeNameToSearch);
    }

    public class QueryTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String routeName = params[0];
            StringBuilder stringBuilder = new StringBuilder("https://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei/");
            stringBuilder.append(routeName).append("?$format=JSON");
            String strUrl = stringBuilder.toString();
            String encodedStrUrl = null;
            try{
                encodedStrUrl = URLEncoder.encode(strUrl, "UTF-8");
            }catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(encodedStrUrl);
            String jsonResponse = null;
            String[] parsedRouteData = null;
            URL url = NetworkUtils.buildPtxArrivalTimeQueryUrl(encodedStrUrl);
            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            try {
                parsedRouteData = BusRouteJsonUtils.getSimpleRouteNumberFromJson(jsonResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            */
            return jsonResponse;
        }
        @Override
        protected void onPostExecute(String response) {
            if(response != null){
                mDisplayText.setText(response);
            }else {

            }

        }
    }
}
