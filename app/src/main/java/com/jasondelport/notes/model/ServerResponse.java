package com.jasondelport.notes.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by jasondelport on 19/05/15.
 */
@Parcel
public class ServerResponse {

    @SerializedName("status")
    private String status;
}
