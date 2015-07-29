package com.jasondelport.notes;

import android.app.Application;
import android.util.Log;

import com.jasondelport.notes.dagger.DaggerApplicationComponent;
import com.jasondelport.notes.dagger.DataServiceComponent;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.otto.Bus;

import timber.log.Timber;

/**
 * Created by jasondelport on 22/02/2015.
 */
public class App extends Application {

    private static Bus sBus;
    private static DataServiceComponent sDataServiceComponent;

    public static Bus getEventBus() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }

    private static App sInstance;

    public static App getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (sDataServiceComponent == null) {
            sDataServiceComponent = DaggerApplicationComponent.create();
        }

        sInstance = this;
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            /*
            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
            */
        }
    }

    public static DataServiceComponent component() {
        return sDataServiceComponent;
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.i("onLowMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Timber.i("onTerminate");
    }
}
