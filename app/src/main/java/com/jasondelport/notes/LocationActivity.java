package com.jasondelport.notes;

import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jasondelport.notes.location.LocationProvider;
import com.jasondelport.notes.model.CustomLocation;
import com.jasondelport.notes.model.Locations;
import com.jasondelport.notes.util.LogUtils;
import com.jasondelport.notes.util.Utils;

import timber.log.Timber;

public class LocationActivity extends ActionBarActivity implements OnMapReadyCallback, LocationProvider.CustomLocationCallback {
    private GoogleMap mMap;
    private LocationProvider mLocationProvider;
    private MediaPlayer mp;
    private int mType = GoogleMap.MAP_TYPE_NORMAL;
    private static boolean zoomed;
    private static String mCurrentLocation;

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
        int bearing;
        int accuracy = 25;
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
            if (dist[0] < accuracy) {
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
}