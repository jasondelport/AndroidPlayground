package com.jasondelport.notes;

import android.app.Application;

import com.github.anrwatchdog.ANRWatchDog;
import com.jasondelport.notes.dagger.DaggerApplicationComponent;
import com.jasondelport.notes.dagger.DataServiceComponent;
import com.squareup.otto.Bus;

import timber.log.Timber;

/**
 * Created by jasondelport on 22/02/2015.
 */
public class PlaygroundApp extends Application {

    private static Bus sBus;
    private static DataServiceComponent sDataServiceComponent;

    public static Bus getEventBus() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }

    private static PlaygroundApp sInstance;

    public static PlaygroundApp getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (sDataServiceComponent == null) {
            sDataServiceComponent = DaggerApplicationComponent.create();
        }

        sInstance = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            new ANRWatchDog().setANRListener(error -> Timber.e(error, "Application Not Responding")).start();
        }
    }


    public static DataServiceComponent getsDataServiceComponent() {
        return sDataServiceComponent;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.w("onLowMemory");
    }

    @Override
    public void onTerminate() {
        Timber.e("onTerminate");
        super.onTerminate();
    }
}
