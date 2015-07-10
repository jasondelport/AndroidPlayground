package com.jasondelport.notes.data.singleton;

import android.content.Context;

import com.jasondelport.notes.App;

/**
 * Created by jasondelport on 10/07/15.
 */
public class ExampleSingleton {

    private static ExampleSingleton sInstance;
    private Context mContext;

    /*

    “There is normally no need to subclass Application. In most situation,
    static singletons can provide the same functionality in a more modular way.
    If your singleton needs a global context (for example to register broadcast
    receivers), the function to retrieve it can be given a Context which
    internally uses Context.getApplicationContext() when first constructing
    the singleton.”

    the below example is from Google/Android employee Dianne Hackborn

    static final Object sLock = new Object();
    static MySingleton sInstance;

    static MySingleton getInstance(Context context) {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new MySingleton(
                        context.getApplicationContext());
            }
            return sInstance;
        }
    }

     */

    private ExampleSingleton() {
        if (mContext == null) {
            mContext = App.getContext();
        }
    }

    // this pattern is called 'double-checked locking'. synchronisation is expensive so only do it if you have to
    public static ExampleSingleton getInstance() {
        if (sInstance == null) sInstance = getSynchronizedInstance();
        return sInstance;
    }

    private static synchronized ExampleSingleton getSynchronizedInstance() {
        if (sInstance == null) sInstance = new ExampleSingleton();
        return sInstance;
    }

    private String mHelloWorld;

    public String getHelloWorld() {
        return mHelloWorld;
    }

    public void setHelloWorld(String helloWorld) {
        this.mHelloWorld = helloWorld;
    }
}
