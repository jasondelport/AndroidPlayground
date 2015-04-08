package com.jasondelport.notes.network;

import com.jasondelport.notes.model.Data;

import retrofit.Callback;
import retrofit.http.GET;

public interface Api {
    @GET("/api/notes")
    void getNotes(Callback<Data> callback);
}