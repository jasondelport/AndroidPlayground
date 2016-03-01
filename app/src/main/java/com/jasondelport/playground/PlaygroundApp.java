package com.jasondelport.playground;

import android.app.Application;
import android.content.Intent;

import com.github.anrwatchdog.ANRWatchDog;
import com.jasondelport.playground.dagger.DaggerApplicationComponent;
import com.jasondelport.playground.dagger.DataServiceComponent;
import com.squareup.otto.Bus;

import timber.log.Timber;

/**
 * Created by jasondelport on 22/02/2015.
 */
public class PlaygroundApp extends Application {

    private static Bus sBus;
    private static DataServiceComponent sDataServiceComponent;
    private static PlaygroundApp sInstance;

    public static Bus getEventBus() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }

    public static PlaygroundApp getContext() {
        return sInstance;
    }

    public static DataServiceComponent getsDataServiceComponent() {
        return sDataServiceComponent;
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

        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> handleUncaughtException(thread, e));
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically


        Intent intent = new Intent ();
        intent.setAction ("com.mydomain.SEND_LOG");
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


        /*
         <activity
            android:name="com.mydomain.SendLog"
            <intent-filter>
              <action android:name="com.mydomain.SEND_LOG" />
            </intent-filter>
        </activity>
        */

        System.exit(1); // kill off the crashed app
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
