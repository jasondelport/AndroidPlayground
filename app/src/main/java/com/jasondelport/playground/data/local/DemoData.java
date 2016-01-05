package com.jasondelport.playground.data.local;

import android.os.Handler;

import com.jasondelport.playground.PlaygroundApp;
import com.jasondelport.playground.R;
import com.jasondelport.playground.listener.OnFinishedListener;

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
