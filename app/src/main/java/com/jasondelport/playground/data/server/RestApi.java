package com.jasondelport.playground.data.server;

import com.jasondelport.playground.data.model.Note;
import com.jasondelport.playground.data.model.NoteData;
import com.jasondelport.playground.data.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


public interface RestApi {

    @POST("notes/add")
    Call<Note> addNote(@Body Note note);

    @GET("notes/delete/{id}")
    Call<ServerResponse> deleteNote(@Path("id") String noteId);

    @GET("notes")
    Observable<NoteData> getNotes();

}