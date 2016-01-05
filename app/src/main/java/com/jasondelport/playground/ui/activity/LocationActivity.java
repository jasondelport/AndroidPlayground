package com.jasondelport.playground.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jasondelport.playground.PlaygroundApp;
import com.jasondelport.playground.R;
import com.jasondelport.playground.data.location.LocationProvider;
import com.jasondelport.playground.data.model.CustomLocation;
import com.jasondelport.playground.data.model.LocationManager;
import com.jasondelport.playground.event.LocationUpdateEvent;
import com.jasondelport.playground.util.LogUtils;
import com.jasondelport.playground.util.Utils;
import com.squareup.otto.Subscribe;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import icepick.Icepick;
import icepick.State;
import timber.log.Timber;

public class LocationActivity extends BaseActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    final private int REQUEST_PERMISSIONS = 1001;
    @State
    ArrayList<LatLng> mLocationHistory;
    private boolean zoomed;
    private String mCurrentLocation;
    private GoogleMap mMap;
    private LocationProvider mLocationProvider;
    private int mType = GoogleMap.MAP_TYPE_NORMAL;
    private List<CustomLocation> mLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_location);

        mLocations = LocationManager.getInstance().getLocations();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        zoomed = false;

        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        if (mLocationProvider == null) {
                            mLocationProvider = new LocationProvider();
                        }
                    } else {
                        Toast.makeText(LocationActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                });

        /*
        int hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(LocationActivity.this, "Permission Denied FOREVER", Toast.LENGTH_LONG).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS);
            }
            return;
        } else {
            if (mLocationProvider == null) {
                mLocationProvider = new LocationProvider();
            }
        }
        */

    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mLocationProvider == null) {
                        mLocationProvider = new LocationProvider();
                    }
                } else {
                    Toast.makeText(LocationActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    */

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

        if (mLocationHistory == null) {
            mLocationHistory = new ArrayList<>();
            mLocationHistory.add(latLng);
        } else {
            LatLng lastLocation = mLocationHistory.get(mLocationHistory.size() - 1);
            float[] dist = new float[1];
            Location.distanceBetween(
                    location.getLatitude(), location.getLongitude(),
                    lastLocation.latitude, lastLocation.longitude,
                    dist);
            if (dist[0] > 25 && accuracy < 15) {
                mLocationHistory.add(latLng);
            }
        }

        //Timber.d(String.valueOf(location));

        float[] dist = new float[1];
        for (CustomLocation customLocation : mLocations) {
            Location.distanceBetween(
                    location.getLatitude(), location.getLongitude(),
                    customLocation.getLatitude(), customLocation.getLongitude(),
                    dist);
            if (dist[0] < 15 && !customLocation.getName().equals(mCurrentLocation)) {
                Utils.makeToast(this, customLocation.getName());
                mCurrentLocation = customLocation.getName();
                if (!customLocation.getName().equals("My Jol")) {
                    LogUtils.appendLog(customLocation.getName());
                }
            }
        }

        /*
        PolylineOptions options = new PolylineOptions().width(2).color(Color.BLACK);
        for (int i = 0; i < mLocationHistory.size(); i++) {
            LatLng historyLatLng = mLocationHistory.get(i);
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
        if (mLocationProvider != null) {
            mLocationProvider.connect();
        }
        PlaygroundApp.getEventBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationProvider != null) {
            mLocationProvider.disconnect();
        }
        PlaygroundApp.getEventBus().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        if (mLocationProvider != null) {
            mLocationProvider = null;
        }
        if (mMap != null) {
            mMap.clear();
            mMap = null;
        }

        if (mLocationHistory != null) {
            mLocationHistory.clear();
            mLocationHistory = null;
        }

        if (mLocations != null) {
            mLocations.clear();
            mLocations = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        for (CustomLocation loc : mLocations) {
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
        Timber.d("onSaveInstanceState");
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.d("onLowMemory");
        if (mMap != null) {
            mMap.clear();
        }
    }
}