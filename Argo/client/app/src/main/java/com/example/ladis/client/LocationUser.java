package com.example.ladis.client;

import android.location.Location;

/**
 * Created by ladis on 27/06/2018.
 */

public class LocationUser {

    private Location location;

    public LocationUser()
    {
        location=new Location("");
        location.setLatitude(45.491371);
        location.setLongitude(12.253303);
    }
    public LocationUser(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



}
