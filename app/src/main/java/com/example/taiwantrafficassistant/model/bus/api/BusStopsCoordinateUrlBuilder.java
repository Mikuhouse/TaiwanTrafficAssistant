package com.example.taiwantrafficassistant.model.bus.api;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.taiwantrafficassistant.R;
import com.example.taiwantrafficassistant.controller.bus.NearbyStop.BusNearbyStopOnMapActivity;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URL;
import java.net.URLEncoder;

import static android.content.ContentValues.TAG;



public class BusStopsCoordinateUrlBuilder {
    //static final String base0 = "http://140.136.149.241/tests/gapi.php?url=";
    static final String base1 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    static final String base2 = "&radius=500&type=bus_station&key=AIzaSyBdyPsDGrzILPd5nxiX4waEIhS83SvoD4I&language=zh-TW";

    public static URL buildUrl(String query){
        StringBuilder resultStrB = new StringBuilder(base1);
        resultStrB.append(query).append(base2);
        String resultStr = resultStrB.toString();
        /*
        try {
            resultStr = URLEncoder.encode(resultStr, "UTF-8");
        }catch (Exception e){

        }
           */
        URL resultUrl = null;
        try{
            resultUrl = new URL(resultStr);
        }catch (Exception e){
            System.out.println("Error: resultUrl = new URL(resultStr);");
        }
        return  resultUrl;
    }
}
