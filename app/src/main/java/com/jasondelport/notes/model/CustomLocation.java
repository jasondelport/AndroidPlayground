package com.jasondelport.notes.model;

/**
 * Created by jasondelport on 13/04/15.
 */
public class CustomLocation {
    private double latitude;
    private double longitude;
    private String name;
    private int audio;

    public CustomLocation(String name) {
        this.name = name;
    }

    public void setLatitudeLongitude(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }
}
