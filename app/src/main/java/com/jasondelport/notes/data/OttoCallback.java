package com.jasondelport.notes.data;

import com.jasondelport.notes.App;
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
        App.getEventBus().post(new NetworkSuccessEvent(t, response));
    }

    @Override
    public void failure(RetrofitError error) {
        App.getEventBus().post(new NetworkErrorEvent(error));
    }
}
