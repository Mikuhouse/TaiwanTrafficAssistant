package com.example.taiwantrafficassistant.controller.bus.NearbyStop;

import com.example.taiwantrafficassistant.controller.bus.routeSearch.BusRouteInformation;

public class BusStopInformation {
    private double latitude = 0;
    private double longitude = 0;
    private String stopName = "";
    private String stopUid = "";
    public BusStopInformation(double latitude, double longitude, String stopName){
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopName = stopName;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public String getStopName(){
        return stopName;
    }
    String getStopUid(){
        return stopUid;
    }

}
