package com.example.taiwantrafficassistant.model.bus.api;

import java.net.URL;

public class BusStopsCoordinateUrlBuilder {
    static final String base1 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    static final String base2 = "&radius=50&type=bus_station&key=AIzaSyBdyPsDGrzILPd5nxiX4waEIhS83SvoD4I&language=zh-TW";
    public static URL buildUrl(String query){
        StringBuilder resultStr = new StringBuilder(base1);
        resultStr.append(query).append(base2);

        URL resultUrl = null;
        try{
            resultUrl = new URL(resultStr.toString());
        }catch (Exception e){
            System.out.println("Error: resultUrl = new URL(resultStr);");
        }
        return  resultUrl;
    }
}
