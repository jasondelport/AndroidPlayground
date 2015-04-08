package com.jasondelport.notes.network;

import com.jasondelport.notes.model.Data;
import com.jasondelport.notes.model.Note;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface Api {
    @GET("/notes")
    void getNotes(Callback<Data> callback);

    @POST("/notes/add")
    void addNote(@Body Note note, Callback<Note> callback);
}