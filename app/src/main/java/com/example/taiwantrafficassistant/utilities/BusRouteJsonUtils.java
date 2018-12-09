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

    //public static String[]
//    public static String[] getInformationOfRouteNumber(String oringinalJson, String routeNumber)
////            throws JSONException {
////        JSONArray jsonArray = new JSONArray(oringinalJson);
////        for(int i = 0;i < jsonArray.length();i++){
////
////        }
////
////    }
}
