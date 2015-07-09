package com.jasondelport.notes.data.server;

import com.jasondelport.notes.data.model.Note;
import com.jasondelport.notes.data.model.NoteData;
import com.jasondelport.notes.data.model.ServerResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface RestApi {
    //@GET("/notes")
    //void getNotes(Callback<NoteData> callback);

    @POST("/notes/add")
    void addNote(@Body Note note, Callback<Note> callback);

    @GET("/notes/delete/{id}")
    void deleteNote(@Path("id") String noteId, Callback<ServerResponse> callback);

    @GET("/notes")
    Observable<NoteData> getNotes();

    /*

    RXJava Version

    @POST("/notes/add")
    Observable<Note> addNote(@Body Note note);

    @GET("/notes/delete/{id}")
    Observable<ServerResponse> deleteNote(@Path("id") String noteId);

     */
}