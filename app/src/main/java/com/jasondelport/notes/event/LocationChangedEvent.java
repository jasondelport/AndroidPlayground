package com.jasondelport.notes.event;

import android.location.Location;

/**
 * Created by jasondelport on 05/05/15.
 */
public class LocationChangedEvent {

    private Location location;

    public LocationChangedEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
