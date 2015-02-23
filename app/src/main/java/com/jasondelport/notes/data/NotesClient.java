package com.jasondelport.notes.data;

import com.jasondelport.notes.BuildConfig;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import timber.log.Timber;

/**
 * Created by jasondelport on 23/02/2015.
 */
public class NotesClient {

    private RestAdapter restAdapter;

    public NotesClient() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.URL)
                .build();
    }

    interface ListNotes {
        @GET("/notes")
        void getNotesFromServer(Callback<Object> callback);
    }

    public void getNotes() {
        ListNotes service = restAdapter.create(ListNotes.class);
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {

                Timber.d("json - > %s", o.toString());
            }

            @Override
            public void failure(RetrofitError e) {
                Timber.d("error -> %s", e.toString());
            }
        };
        service.getNotesFromServer(callback);
    }
}
