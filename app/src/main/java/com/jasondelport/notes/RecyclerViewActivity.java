package com.jasondelport.notes;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.jasondelport.notes.event.NetworkErrorEvent;
import com.jasondelport.notes.event.NetworkSuccessEvent;
import com.jasondelport.notes.model.NoteData;
import com.jasondelport.notes.network.NetworkClient;
import com.jasondelport.notes.network.OttoCallback;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import timber.log.Timber;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteData mNoteData;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        //mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (savedInstanceState != null) {
            mNoteData = Parcels.unwrap(savedInstanceState.getParcelable("data"));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("registering event bus");
        App.getEventBus().register(this);
        if (mNoteData != null) {
            setData();
        } else {
            getData();
        }
    }

    private void setData() {
        Timber.d("setting data");
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter = new RecyclerViewAdapter(mNoteData.getNotes());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData() {
        Timber.d("getting data");
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        NetworkClient.getService().getNotes(new OttoCallback<NoteData>());
    }

    @Subscribe
    public void onNetworkSuccess(NetworkSuccessEvent<NoteData> event) {
        Timber.d("network success -> %s", event.getResponse().toString());
        if (event.getData() instanceof NoteData) {
            mNoteData = event.getData();
            setData();
        } else {
            Timber.w("item deleted");
            getData();
        }
    }

    @Subscribe
    public void onNetworkError(NetworkErrorEvent event) {
        mProgressBar.setVisibility(View.GONE);
        Timber.e(event.getError(), "Connection Error");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("unregistering event bus");
        App.getEventBus().unregister(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Parcelable parcelData = Parcels.wrap(mNoteData);
        outState.putParcelable("data", parcelData);
        super.onSaveInstanceState(outState);
    }
}