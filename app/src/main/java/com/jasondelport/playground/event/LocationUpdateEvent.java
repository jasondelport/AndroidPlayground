package com.jasondelport.playground.event;

import android.location.Location;

/**
 * Created by jasondelport on 05/05/15.
 */
public class LocationUpdateEvent {

    private Location location;

    public LocationUpdateEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
