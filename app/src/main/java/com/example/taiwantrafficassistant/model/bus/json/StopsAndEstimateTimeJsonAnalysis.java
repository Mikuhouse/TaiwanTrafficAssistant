package com.example.taiwantrafficassistant.model.bus.json;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops.StopsAndEstimatedTimeInformation;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

public class StopsAndEstimateTimeJsonAnalysis {
    private static String mBaseUrlOne = "http://140.136.149.241/tests/routeEst.php?route=";
    private static String mBaseUrlTwo = "&dir=";
    private static String mBaseUrlThree = "&ID=a55c64f5cf2d46c2908ed29af853880c&KEY=rL4dhGhxs7smLvAtSusvXF2qPcI";
    public static URL buildUrl(String query, String dir){
        StringBuilder tmp = new StringBuilder(mBaseUrlOne);
        tmp.append(query).append(mBaseUrlTwo).append(dir).append(mBaseUrlThree);

        URL result = null;
        try {
            result = new URL(tmp.toString());
        }catch (Exception e){
            System.out.println("Error:StopsAndEstimateTimeJsonAnalysis.buildUrl");
        }
        return result;
    }
    public static List<StopsAndEstimatedTimeInformation> analysisResponse(String jsonResponse){
        List<StopsAndEstimatedTimeInformation> result = null;
        try {
            result = new ArrayList<>();
            JSONArray origin = new JSONArray(jsonResponse);
            for(int i = 0;i < origin.length();i++){
                JSONObject listItem = origin.getJSONObject(i);
                String stopName = listItem.getString("StopName");
                int estimatedTime = listItem.getInt("EstimateTime");
                result.add(new StopsAndEstimatedTimeInformation(stopName, estimatedTime));
            }
        }catch (Exception e){
            System.out.println("Error: List<StopsAndEstimatedTimeInformation>");
        }
        return result;
    }
}
