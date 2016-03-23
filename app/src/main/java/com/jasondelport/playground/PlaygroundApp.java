package com.jasondelport.playground;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.github.anrwatchdog.ANRWatchDog;
import com.jasondelport.playground.dagger.DaggerApplicationComponent;
import com.jasondelport.playground.dagger.DataServiceComponent;
import com.jasondelport.playground.ui.activity.MainActivity;
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

    private Thread.UncaughtExceptionHandler defaultUEH;

    @Override
    public void onCreate() {
        super.onCreate();

        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();

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

    public void handleUncaughtException(Thread thread, Throwable ex) {
        Timber.e(ex, "An uncaught exception was handled by the global exception handler.");

        PendingIntent intent = PendingIntent.getActivity(getContext(),
                1001, new Intent(getContext(), MainActivity.class),
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10000, intent);

        System.exit(2);

        defaultUEH.uncaughtException(thread, ex);
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
