package com.example.taiwantrafficassistant.bus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.utilities.BusRouteJsonUtils;
import com.example.taiwantrafficassistant.utilities.NetworkUtils;
import com.example.taiwantrafficassistant.utilities.BusRouteJsonUtils;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class   BusTimeOfArrivalActivity extends AppCompatActivity {

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
            try{
                routeName = URLEncoder.encode(routeName, "UTF-8");
            }catch (Exception e){
                System.out.println("URLencoding failed");
            }

            URL url = NetworkUtils.buildPtxBusStop(routeName);
            System.out.println(url.toString());
            String jsonResponse = null;
            String[] stopsList = new String[1];
            try{
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                stopsList = BusRouteJsonUtils.getSimpleRouteStopFromJson(jsonResponse);
            }catch(Exception e){
                System.out.println("Error");
            }
            String tmp = stopsList[0];
            return tmp;

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
