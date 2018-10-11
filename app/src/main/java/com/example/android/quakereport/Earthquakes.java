package com.example.android.quakereport;

/**
 * Created by Kamal Dev Sharma on 6/28/2017.
 */

public class Earthquakes  {
    Double magnitude;
    String place;
    Long time;
    String url;

    public Earthquakes(double magnitude, String place, Long time, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.time = time;
        this.url = url;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
