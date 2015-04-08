package com.jasondelport.notes;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jasondelport.notes.model.Data;
import com.jasondelport.notes.model.Note;
import com.jasondelport.notes.network.NetworkClient;

import org.parceler.Parcels;

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
    private Data mData;

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

        if (savedInstanceState != null) {
            mData = Parcels.unwrap(savedInstanceState.getParcelable("data"));
        }

        /*
        Note note = new Note();
        note.setNote("hello world");

        NetworkClient.getService().addNote(note, new Callback<Note>() {
            @Override
            public void success(Note note, Response response) {
                Timber.d("new note -> %s", note.getText());
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.d("error -> %s", error.toString());
            }
        });
        */

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mData != null) {
            setData();
        } else {
            getData();
        }
    }

    private void setData() {
        Timber.d("setting data");
        mAdapter = new RecyclerViewAdapter(mData.getNotes());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData() {
        Timber.d("getting data");
        NetworkClient.getService().getNotes(new Callback<Data>() {
            @Override
            public void success(Data data, Response response) {
                mData = data;
                setData();
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.d("error -> %s", error.toString());
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Parcelable parcelData = Parcels.wrap(mData);
        outState.putParcelable("data", parcelData);
        super.onSaveInstanceState(outState);
    }
}