package com.example.taiwantrafficassistant.controller.bus.estimatedTimeAndStops;

public class Information {
    private String mStopName;
    private int mEstimatedTime;

    public Information() {
        super();
    }
    public Information(String stopName, int estimatedTime) {
        super();
        this.mStopName = stopName;
        this.mEstimatedTime = estimatedTime;
    }
    public String getStopName(){
        return mStopName;
    }
    public void setStopName(String stopName){
        this.mStopName = stopName;
    }
    public int getEstimatedTime(){
        return mEstimatedTime;
    }
    public void setEstimatedTime(int estimatedTime){
        this.mEstimatedTime = estimatedTime;
    }
}
