package com.jasondelport.playground.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import timber.log.Timber;

/**
 * Created by jasondelport on 02/06/16.
 */
public class OnClearFromRecentService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("Service Destroyed");
    }

    public void onTaskRemoved(Intent rootIntent) {
        Timber.w("Task Removed");
        // this gets called when the application is cleared from recents menu
        stopSelf();
    }
}