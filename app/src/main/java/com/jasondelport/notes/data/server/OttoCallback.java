package com.jasondelport.notes.data.server;

import com.jasondelport.notes.PlaygroundApp;
import com.jasondelport.notes.event.NetworkErrorEvent;
import com.jasondelport.notes.event.NetworkSuccessEvent;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class OttoCallback<T> implements Callback<T> {


    public OttoCallback() {
    }

    @Override
    public void success(T t, Response response) {
        Timber.d("callback network success");
        PlaygroundApp.getEventBus().post(new NetworkSuccessEvent(t, response));
    }

    @Override
    public void failure(RetrofitError error) {
        PlaygroundApp.getEventBus().post(new NetworkErrorEvent(error));
    }
}
