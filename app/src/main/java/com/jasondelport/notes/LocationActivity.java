package com.jasondelport.notes;

import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.jasondelport.notes.location.LocationProvider;
import com.jasondelport.notes.model.CustomLocation;
import com.jasondelport.notes.model.Locations;
import com.jasondelport.notes.util.Utils;

import timber.log.Timber;

public class LocationActivity extends ActionBarActivity implements OnMapReadyCallback, LocationProvider.LocationCallback {
    private GoogleMap mMap;
    private LocationProvider mLocationProvider;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationProvider = new LocationProvider(this, this);
    }

    private void playAudio(int id) {
        cleanUpAudio();
        mp = MediaPlayer.create(this, id);
        mp.start();
    }

    private void cleanUpAudio() {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }

    public void showLocation(Location location) {
        Timber.d(String.valueOf(location));
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        float [] dist = new float[1];
        for (CustomLocation cl : Locations.getLocations()) {
            Location.distanceBetween(
                    location.getLatitude(),location.getLongitude(),
                    cl.getLatitude(),cl.getLongitude(),
                    dist);
            if (dist[0] < 25) {
                Utils.makeToast(this, cl.getName());
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationProvider.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationProvider.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        mMap = map;
    }
}