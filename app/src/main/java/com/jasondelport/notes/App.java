package com.jasondelport.notes;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.otto.Bus;

import timber.log.Timber;

/**
 * Created by jasondelport on 22/02/2015.
 */
public class App extends Application {

    private static Bus bus;

    public static Bus getEventBus() {
        if (bus == null) {
            bus = new Bus();
        }
        return bus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {

        }

        @Override
        public void i(Throwable t, String message, Object... args) {

        }

        @Override
        public void e(String message, Object... args) {

        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);

        }
    }
}
