package com.jasondelport.playground.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by jasondelport on 23/03/16.
 */
@AutoValue
public abstract class Money implements Parcelable {
    public abstract String currency();
    public abstract long amount();
    public static Money create(String currency, long amount) {
        return new AutoValue_Money(currency, amount);
    }
    public String displayString() {
        return currency() + amount();
    }
}