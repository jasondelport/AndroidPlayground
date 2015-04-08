package com.jasondelport.notes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jasondelport.notes.model.Data;
import com.jasondelport.notes.model.Note;
import com.jasondelport.notes.network.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class RecyclerViewActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Note> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RestClient.getApiService().getNotes(new Callback<Data>() {
            @Override
            public void success(Data data, Response response) {
                mNotes = data.getNotes();
                mAdapter = new RecyclerViewAdapter(mNotes);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.d("error -> %s", error.toString());
            }
        });


    }
}