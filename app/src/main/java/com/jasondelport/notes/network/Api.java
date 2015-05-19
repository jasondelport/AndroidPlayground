package com.jasondelport.notes.network;

import com.jasondelport.notes.model.Note;
import com.jasondelport.notes.model.NoteData;
import com.jasondelport.notes.model.ServerResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface Api {
    @GET("/notes")
    void getNotes(Callback<NoteData> callback);

    @POST("/notes/add")
    void addNote(@Body Note note, Callback<Note> callback);

    @GET("/notes/delete/{id}")
    void deleteNote(@Path("id") String noteId, Callback<ServerResponse> callback);
}