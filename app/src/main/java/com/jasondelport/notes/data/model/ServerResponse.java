package com.jasondelport.notes.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by jasondelport on 19/05/15.
 */
@Parcel
public class ServerResponse {

    @SerializedName("status")
    public String status;
}
