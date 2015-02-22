package com.jasondelport.notes;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import timber.log.Timber;


public class MainFragment extends Fragment {

    private Activity activity;

    private MainFragmentState state = MainFragmentState.getInstance();

    public MainFragment() {
        lifecycle("constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // skips this lifecycle method and onDestroy when the fragment gets brought to the fore
        lifecycle("onCreate");
        // initialise data here
        state.setNote(new Note());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lifecycle("onCreateView");
        // initialise view here
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifecycle("onActivityCreated");
        if (savedInstanceState != null) {
            Note note = Parcels.unwrap(savedInstanceState.getParcelable("note"));
            state.setNote(note);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");
        Parcelable wrappedNote = Parcels.wrap(state.getNote());
        outState.putParcelable("note", wrappedNote);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle("onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycle("onResume");

        if (isAdded() || activity!=null) {
            // some task that involves getActivity(), use activity instead
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycle("onPause");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        lifecycle("onAttach");
        this.activity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifecycle("onDetach");
    }

    private void lifecycle(String methodName) {
        Timber.d("Method Name -> %s", methodName);
    }

}
