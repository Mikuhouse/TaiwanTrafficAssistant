package com.example.taiwantrafficassistant.model.bus.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusRouteJsonUtils {
    public static String[] getSimpleRouteNumberFromJson(String numberSearchResult)
            throws JSONException {
        //JSONObject busRouteInformationJson = new JSONObject(numberSearchResult);
        JSONArray busRouteArray = new JSONArray(numberSearchResult);
        String[] parsedRouteData = new String[busRouteArray.length()];
        for (int i = 0; i < busRouteArray.length(); i++) {
            JSONObject jsonObject = busRouteArray.getJSONObject(i);
            JSONObject SubRouteName = jsonObject.getJSONObject("RouteName");
            String routeText = SubRouteName.getString("Zh_tw");
            System.out.println(routeText);
            //Log.d("TAG", "title:" + title + ", tag:" + tag + ", info:" + info);
            parsedRouteData[i] = routeText;
        }
        return parsedRouteData;
    }

    public static String[] getSimpleRouteStopFromJson(String stopResult)
            throws JSONException {
        System.out.println(stopResult);
        JSONArray original = new JSONArray(stopResult);
        System.out.println("original: " + original.toString());
        JSONObject tmp = original.getJSONObject(0);
        System.out.println("tmp:" + tmp.toString());
        JSONArray stopsArray = tmp.getJSONArray("Stops");
        System.out.println(stopsArray.length());
        String[] parsedRouteData = new String[stopsArray.length()];

        for (int i = 0; i < stopsArray.length(); i++) {
            JSONObject jsonObject = stopsArray.getJSONObject(i);
            JSONObject stopName = jsonObject.getJSONObject("StopName");
            String stopText = stopName.getString("Zh_tw");
            System.out.println(stopText);
            //Log.d("TAG", "title:" + title + ", tag:" + tag + ", info:" + info);
            parsedRouteData[i] = stopText;
        }

        return parsedRouteData;

    }
}
