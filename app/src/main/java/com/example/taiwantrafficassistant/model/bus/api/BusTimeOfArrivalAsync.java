package com.example.taiwantrafficassistant.model.bus.api;

import android.os.AsyncTask;

import com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops.Information;
import com.example.taiwantrafficassistant.model.bus.json.BusRouteJsonUtils;
import com.example.taiwantrafficassistant.model.utilities.network.NetworkUtils;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class BusTimeOfArrivalAsync extends AsyncTask<String, Void, String> {
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
            //mDisplayText.setText(response);
        }else {

        }
    }
}

