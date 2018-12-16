package com.example.taiwantrafficassistant.utilities;

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
        JSONObject original = new JSONObject(stopResult);
        JSONArray stopsArray = original.getJSONArray("Stops");
        String[] parsedRouteData = new String[stopsArray.length()];
        for (int i = 0; i < stopsArray.length(); i++) {
            JSONObject jsonObject = stopsArray.getJSONObject(i);
            JSONObject StopName = jsonObject.getJSONObject("StopName");
            String stopText = StopName.getString("Zh_tw");
            System.out.println(stopText);
            //Log.d("TAG", "title:" + title + ", tag:" + tag + ", info:" + info);
            parsedRouteData[i] = stopText;
        }
        return parsedRouteData;

    }
}
