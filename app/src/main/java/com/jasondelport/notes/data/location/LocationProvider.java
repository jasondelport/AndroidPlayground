package com.jasondelport.notes.data.location;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.jasondelport.notes.App;
import com.jasondelport.notes.event.LocationUpdateEvent;


public class LocationProvider implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private FusedLocationCallback mFusedLocationCallback = new FusedLocationCallback();


    public LocationProvider(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(4 * 1000)
                .setMaxWaitTime(8 * 1000)
                .setFastestInterval(2 * 1000);

        mContext = context;
    }

    public void connect() {
        mGoogleApiClient.connect();
        App.getEventBus().register(this);
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mFusedLocationCallback);
            mGoogleApiClient.disconnect();
        }
        App.getEventBus().unregister(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            App.getEventBus().post(new LocationUpdateEvent(location));
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mFusedLocationCallback, null);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution() && mContext instanceof Activity) {
            try {
                Activity activity = (Activity) mContext;
                connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    private class FusedLocationCallback extends LocationCallback {
        private boolean isAvailable;

        public void onLocationAvailability(LocationAvailability locationAvailability) {
            isAvailable = locationAvailability.isLocationAvailable();
        }

        public void onLocationResult(LocationResult result) {
            if (isAvailable) {
                App.getEventBus().post(new LocationUpdateEvent(result.getLastLocation()));
            }
        }

    }
}