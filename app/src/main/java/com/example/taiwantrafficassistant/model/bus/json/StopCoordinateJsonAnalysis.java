package com.example.taiwantrafficassistant.model.bus.json;

import com.example.taiwantrafficassistant.controller.bus.NearbyStop.BusStopInformation;
import com.example.taiwantrafficassistant.model.bus.api.BusStopsCoordinateUrlBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StopCoordinateJsonAnalysis {
    public static List<BusStopInformation> analsys(String jsonResponse){
        List<BusStopInformation> resultList = null;
        try {
            resultList = new ArrayList<>();
            JSONObject origin = new JSONObject(jsonResponse);
            JSONArray result = origin.getJSONArray("results");
            for(int i = 0;i < result.length();i++){
                JSONObject information = result.getJSONObject(i);
                String stopName = information.getString("name");
                JSONObject geo = information.getJSONObject("geometry");
                JSONObject loc = geo.getJSONObject("location");
                double lat = loc.getDouble("lat");
                double lng = loc.getDouble("lng");
                resultList.add(new BusStopInformation(lat, lng, stopName));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
}
