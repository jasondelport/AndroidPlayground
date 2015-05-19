package com.jasondelport.notes;

import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jasondelport.notes.event.LocationUpdateEvent;
import com.jasondelport.notes.location.LocationProvider;
import com.jasondelport.notes.model.CustomLocation;
import com.jasondelport.notes.model.Locations;
import com.jasondelport.notes.util.LogUtils;
import com.jasondelport.notes.util.Utils;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import icepick.Icepick;
import icepick.Icicle;
import timber.log.Timber;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Icicle
    ArrayList<LatLng> locationHistory;
    private boolean zoomed;
    private String mCurrentLocation;
    private GoogleMap mMap;
    private LocationProvider mLocationProvider;
    private MediaPlayer mMediaPlayer;
    private int mType = GoogleMap.MAP_TYPE_NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        zoomed = false;
        if (mLocationProvider == null) {
            mLocationProvider = new LocationProvider(this);
        }

    }

    private void playAudio(int id) {
        cleanUpAudio();
        mMediaPlayer = MediaPlayer.create(this, id);
        mMediaPlayer.start();
    }

    private void cleanUpAudio() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Subscribe
    public void onLocationUpdate(LocationUpdateEvent event) {
        Location location = event.getLocation();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

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
            locationHistory.add(latLng);
        } else {
            LatLng lastLocation = locationHistory.get(locationHistory.size() - 1);
            float[] dist = new float[1];
            Location.distanceBetween(
                    location.getLatitude(), location.getLongitude(),
                    lastLocation.latitude, lastLocation.longitude,
                    dist);
            if (dist[0] > 25 && accuracy < 15) {
                locationHistory.add(latLng);
            }
        }

        Timber.d(String.valueOf(location));



        float[] dist = new float[1];
        for (CustomLocation customLocation : Locations.getLocations()) {
            Location.distanceBetween(
                    location.getLatitude(), location.getLongitude(),
                    customLocation.getLatitude(), customLocation.getLongitude(),
                    dist);
            if (dist[0] < 15) {
                if (!customLocation.getName().equals(mCurrentLocation)) {
                    Utils.makeToast(this, customLocation.getName());
                    mCurrentLocation = customLocation.getName();
                    if (!customLocation.getName().equals("My Jol")) {
                        LogUtils.appendLog(customLocation.getName());
                    }
                }
                //playAudio(cl.getAudio());
            }
        }

        /*
        PolylineOptions options = new PolylineOptions().width(2).color(Color.BLACK);
        for (int i = 0; i < locationHistory.size(); i++) {
            LatLng historyLatLng = locationHistory.get(i);
            options.add(historyLatLng);
        }

        mMap.addPolyline(options);
        */

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
        location.reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationProvider.connect();
        App.getEventBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
        App.getEventBus().unregister(this);

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationProvider != null) {
            mLocationProvider = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        for (CustomLocation loc : Locations.getLocations()) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .title(loc.getName()));
        }
        mMap = map;
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}