package com.jasondelport.notes;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.jasondelport.notes.location.LocationProvider;

public class LocationActivity extends ActionBarActivity implements OnMapReadyCallback, LocationProvider.LocationCallback {
    private GoogleMap mMap;
    private LocationProvider mLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationProvider = new LocationProvider(this, this);
    }

    public void showLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .draggable(true);
//        mMap.addMarker(options);

        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(20)
                .strokeWidth(2)
                .strokeColor(Color.BLUE)
                .fillColor(Color.parseColor("#500084d3"));
        mMap.addCircle(circleOptions);

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
        //map.setMyLocationEnabled(true);
        mMap = map;
    }
}