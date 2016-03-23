package com.jasondelport.playground.data.server;

import com.jasondelport.playground.PlaygroundApp;
import com.jasondelport.playground.event.NetworkErrorEvent;
import com.jasondelport.playground.event.NetworkSuccessEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class OttoCallback<T> implements Callback<T> {


    public OttoCallback() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Timber.d("callback network success");
        PlaygroundApp.getEventBus().post(new NetworkSuccessEvent(response.body(), response));
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        PlaygroundApp.getEventBus().post(new NetworkErrorEvent(t));
    }


}
