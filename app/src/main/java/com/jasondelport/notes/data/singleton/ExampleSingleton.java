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

    from the documentation

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

    "I prefer singletons over Application because it helps keep an app much more organized and
    modular -- instead of having one place where all of your global state across the app needs
    to be maintained, each separate piece can take care of itself. Also the fact that singletons
    lazily initialize (at request) instead of leading you down the path of doing all
    initialization up-front in Application.onCreate() is good.

    There is nothing intrinsically wrong with using singletons. Just use them correctly, when
    it makes sense. The Android framework actually has a lot of them, for it to maintain per-process
    caches of loaded resources and other such things.

    Also for simple applications multithreading doesn't become an issue with singletons, because
    by design all standard callbacks to the app are dispatched on the main thread of the process
    so you won't have multi-threading happening unless you introduce it explicitly through
    threads or implicitly by publishing a content provider or service IBinder to other processes.

    Just be thoughtful about what you are doing. :)" -> Dianne Hackborn

     */
    private static String mHelloWorld;

    private ExampleSingleton() {
        if (mContext == null) {
            mContext = App.getContext();
        }
    }

    // 'double-checked locking' pattern. synchronisation is expensive so only do it if you have to
    public static ExampleSingleton getInstance() {
        if (sInstance == null) sInstance = getSynchronizedInstance();
        return sInstance;
    }

    private static synchronized ExampleSingleton getSynchronizedInstance() {
        if (sInstance == null) sInstance = new ExampleSingleton();
        return sInstance;
    }

    public String getHelloWorld() {
        return mHelloWorld;
    }

    public void setHelloWorld(String helloWorld) {
        this.mHelloWorld = helloWorld;
    }

    public static boolean isNull() {
        if (sInstance == null) {
            return true;
        }
        return false;
    }

    public static void destroy() {
        mHelloWorld = null;
        sInstance = null;
    }
}
