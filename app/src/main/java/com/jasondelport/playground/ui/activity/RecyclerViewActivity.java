package com.jasondelport.playground.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.anupcowkur.reservoir.Reservoir;
import com.jasondelport.playground.PlaygroundApp;
import com.jasondelport.playground.R;
import com.jasondelport.playground.data.model.NoteData;
import com.jasondelport.playground.data.server.DataService;
import com.jasondelport.playground.event.NetworkErrorEvent;
import com.jasondelport.playground.event.NetworkSuccessEvent;
import com.jasondelport.playground.ui.adapter.RecyclerViewAdapter;
import com.jasondelport.playground.ui.dialog.ConfirmDeleteDialogFragment;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class RecyclerViewActivity extends BaseActivity implements ConfirmDeleteDialogFragment.DialogListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteData mNoteData;
    private ProgressBar mProgressBar;
    private Subscription subscription = Subscriptions.empty();
    //private DataService dataService = new DataService();

    @Inject
    DataService dataService;

    // Subscriber implements Observer
    Subscriber<NoteData> subscriber = new Subscriber<NoteData>() {
        @Override
        public void onNext(NoteData data) {
            Timber.d("RXJava onNext");

            mNoteData = data;
            setData();
        }

        @Override
        public void onCompleted() {
            // called after onNext
            Timber.d("RXJava onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, "RXJava onError: %s", e.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        PlaygroundApp.getsDataServiceComponent().inject(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
        PlaygroundApp.getEventBus().register(this);
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
        mAdapter = new RecyclerViewAdapter(this, mNoteData.getNotes());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData() {
        Timber.d("getting data");
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        //NetworkClient.getInstance().getNotes(new OttoCallback<NoteData>());

        // RXJava Version
        Observable<NoteData> observable = dataService.getRestApi().getNotes(); //.cache();
        try {
            Reservoir.put("notes", observable);
        } catch (Exception e) {
            Timber.e(e, "Failed to store notes in cache.");
        }
        subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
    }

    @Subscribe
    public void onNetworkSuccess(NetworkSuccessEvent<NoteData> event) {
        Timber.d("network success -> %s", event.getResponse().toString());
        if (event.getData() != null && event.getData() instanceof NoteData) {
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
        PlaygroundApp.getEventBus().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Parcelable parcelData = Parcels.wrap(mNoteData);
        outState.putParcelable("data", parcelData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfirmDelete() {
        Timber.d("confirm delete listener");
    }
}