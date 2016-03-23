package com.jasondelport.playground.event;



/**
 * Created by jasondelport on 05/05/15.
 */
public class NetworkErrorEvent {

    private Throwable error;

    public NetworkErrorEvent(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }
}
