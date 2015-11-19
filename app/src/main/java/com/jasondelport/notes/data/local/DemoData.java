package com.jasondelport.notes.data.local;

import android.os.Handler;

import com.jasondelport.notes.PlaygroundApp;
import com.jasondelport.notes.R;
import com.jasondelport.notes.listener.OnFinishedListener;

/**
 * Created by jasondelport on 16/07/2015.
 */
public class DemoData {

    public void getCountries(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(PlaygroundApp.getContext().getResources().getStringArray(R.array.countries));
            }
        }, 2000);
    }
}
