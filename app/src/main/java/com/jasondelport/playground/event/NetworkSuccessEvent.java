package com.jasondelport.playground.event;

import retrofit.client.Response;

public class NetworkSuccessEvent<T> {
    private T t;
    private Response response;

    public NetworkSuccessEvent(T t, Response response) {
        this.t = t;
        this.response = response;
    }

    public T getData() {
        return t;
    }

    public Response getResponse() {
        return response;
    }
}
