package com.jasondelport.playground.event;

import retrofit.RetrofitError;

/**
 * Created by jasondelport on 05/05/15.
 */
public class NetworkErrorEvent {

    private RetrofitError error;

    public NetworkErrorEvent(RetrofitError error) {
        this.error = error;
    }

    public RetrofitError getError() {
        return error;
    }
}
