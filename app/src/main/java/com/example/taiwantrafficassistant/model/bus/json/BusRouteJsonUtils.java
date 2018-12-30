package com.example.taiwantrafficassistant.model.bus.json;

import com.example.taiwantrafficassistant.controller.bus.routeSearch.BusRouteInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusRouteJsonUtils {
    public static List<BusRouteInformation> getSimpleRouteNumberFromJson(String jsonResponseOne, String jsonResponseTwo) {
        List<BusRouteInformation> result = null;
        try {
            result = new ArrayList<>();
            JSONArray busRouteArray = new JSONArray(jsonResponseOne);
            for (int i = 0; i < busRouteArray.length(); i++) {
                JSONObject jsonObject = busRouteArray.getJSONObject(i);
                JSONObject routeName = jsonObject.getJSONObject("RouteName");
                String routeText = routeName.getString("Zh_tw");
                String departureStopNameZh = jsonObject.getString("DepartureStopNameZh");
                String destinationStopNameZh = jsonObject.getString("DestinationStopNameZh");
                result.add(new BusRouteInformation(routeText, departureStopNameZh, destinationStopNameZh));
            }

            busRouteArray = new JSONArray(jsonResponseTwo);
            for (int i = 0; i < busRouteArray.length(); i++) {
                JSONObject jsonObject = busRouteArray.getJSONObject(i);
                JSONObject routeName = jsonObject.getJSONObject("RouteName");
                String routeText = routeName.getString("Zh_tw");
                String departureStopNameZh = jsonObject.getString("DepartureStopNameZh");
                String destinationStopNameZh = jsonObject.getString("DestinationStopNameZh");
                result.add(new BusRouteInformation(routeText, departureStopNameZh, destinationStopNameZh));
            }
        }catch (Exception e){
        }
        return result;
    }
}
