package com.example.taiwantrafficassistant.controller.bus.routeSearch;

import java.io.Serializable;

public class BusRouteInformation implements Serializable {
    private String mRouteName;
    private String mDepartureStopName;
    private String mDestinationStopName;


    public BusRouteInformation(String routeName, String DepartureStopName, String DestinationStopName){
        mRouteName = routeName;
        mDepartureStopName = DepartureStopName;
        mDestinationStopName = DestinationStopName;
    }
    public String getRouteName() {
        return mRouteName;
    }
    public String getDepartureStopNam() {
        return mDepartureStopName;
    }
    public String getDestinationStopName() {
        return mDestinationStopName;
    }

}
