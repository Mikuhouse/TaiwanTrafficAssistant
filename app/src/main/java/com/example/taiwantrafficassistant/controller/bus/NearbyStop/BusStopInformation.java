package com.example.taiwantrafficassistant.controller.bus.NearbyStop;

import com.example.taiwantrafficassistant.controller.bus.routeSearch.BusRouteInformation;

public class BusStopInformation {
    private float latitude = 0;
    private float longitude = 0;
    private String stopName = "";
    private String stopUid = "";
    BusStopInformation(float latitude, float longitude, String stopName){
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopName = stopName;
    }
    float getLatitude(){
        return latitude;
    }
    float getLongitude(){
        return longitude;
    }
    String getStopName(){
        return stopName;
    }
    String getStopUid(){
        return stopUid;
    }

}
