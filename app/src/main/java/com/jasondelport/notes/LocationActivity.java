package com.jasondelport.notes;

import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jasondelport.notes.event.LocationUpdateEvent;
import com.jasondelport.notes.location.LocationProvider;
import com.jasondelport.notes.model.CustomLocation;
import com.jasondelport.notes.model.Locations;
import com.jasondelport.notes.util.LogUtils;
import com.jasondelport.notes.util.Utils;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import icepick.Icepick;
import timber.log.Timber;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static boolean zoomed;
    private static String mCurrentLocation;
    private static List<Location> locationHistory;
    private GoogleMap mMap;
    private LocationProvider mLocationProvider;
    private MediaPlayer mp;
    private int mType = GoogleMap.MAP_TYPE_NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationProvider = new LocationProvider(this);

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

    @Subscribe
    public void onLocationUpdate(LocationUpdateEvent event) {
        Location location = event.getLocation();

        int bearing;
        int accuracy = 100;
        String provider = location.getProvider();
        Timber.d("provider -> %s", provider);
        boolean hasBearing = location.hasBearing();
        boolean hasAccuracy = location.hasAccuracy();
        if (hasAccuracy) {
            accuracy = (int) location.getAccuracy();
            Timber.d("accuracy -> %d", accuracy);
        }
        if (hasBearing) {
            bearing = (int) location.getBearing();
            Timber.d("bearing -> %d", bearing);
        }

        if (locationHistory == null) {
            locationHistory = new ArrayList<>();
            locationHistory.add(location);
        } else {
            Location lastLocation = locationHistory.get(locationHistory.size() - 1);
            float[] dist = new float[1];
            Location.distanceBetween(
                    location.getLatitude(), location.getLongitude(),
                    lastLocation.getLatitude(), lastLocation.getLongitude(),
                    dist);
            if (dist[0] > 25 && accuracy < 15) {
                locationHistory.add(location);
            }
        }

        Timber.d(String.valueOf(location));

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        float[] dist = new float[1];
        for (CustomLocation cl : Locations.getLocations()) {
            Location.distanceBetween(
                    location.getLatitude(), location.getLongitude(),
                    cl.getLatitude(), cl.getLongitude(),
                    dist);
            if (dist[0] < 25) {
                if (!cl.getName().equals(mCurrentLocation)) {
                    Utils.makeToast(this, cl.getName());
                    mCurrentLocation = cl.getName();
                    if (!cl.getName().equals("My Jol")) {
                        LogUtils.appendLog(cl.getName());
                    }
                }
                //playAudio(cl.getAudio());
            }
        }

        PolylineOptions options = new PolylineOptions().width(1).color(Color.BLACK);
        for (int i = 0; i < locationHistory.size(); i++) {
            Location loc = locationHistory.get(i);
            options.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
        }
        mMap.addPolyline(options);

        /*
        mMap.addCircle(new CircleOptions()
                .center(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(accuracy)
                .strokeWidth(0.5f)
                .strokeColor(getResources().getColor(R.color.light_blue))
                .fillColor(getResources().getColor(R.color.translucent_light_blue)));
        */
        if (!zoomed) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            zoomed = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationProvider.connect();
        App.getEventBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationProvider.disconnect();
        App.getEventBus().unregister(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        mMap = map;

        for (CustomLocation loc : Locations.getLocations()) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .title(loc.getName()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.normal_map:
                mType = GoogleMap.MAP_TYPE_NORMAL;
                break;

            case R.id.satellite_map:
                mType = GoogleMap.MAP_TYPE_SATELLITE;
                break;

            case R.id.terrain_map:
                mType = GoogleMap.MAP_TYPE_TERRAIN;
                break;

            case R.id.hybrid_map:
                mType = GoogleMap.MAP_TYPE_HYBRID;
                break;
        }

        mMap.setMapType(mType);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}