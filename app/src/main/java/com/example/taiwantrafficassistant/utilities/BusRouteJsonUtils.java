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
    public static String[] getInformationOfRouteNumber(String routeNumber){
        String[] a = new String[1];
        a[1] = new String("");
        return a;
    }
}
