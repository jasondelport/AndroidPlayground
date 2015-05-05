package com.jasondelport.notes.event;

public class NetworkSuccessEvent<T> {
    private T t;

    public NetworkSuccessEvent(T t) {
        this.t = t;
    }

    public T getData() {
        return t;
    }
}
