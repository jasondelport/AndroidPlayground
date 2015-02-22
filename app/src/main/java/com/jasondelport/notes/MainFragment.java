package com.jasondelport.notes;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFragment extends Fragment {

    private Activity activity;
    private String value;

    public MainFragment() {
        notify("constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        notify("onCreate");
        // initialise data here
        value = "value";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notify("onCreateView");
        // initialise view here
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notify("onActivityCreated");
        if (savedInstanceState != null) {
            value = savedInstanceState.getString("key");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        notify("onSaveInstanceState");
        outState.putString("key", value);
    }

    @Override
    public void onStart() {
        super.onStart();
        notify("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        notify("onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        notify("onResume");

        if (isAdded() || activity!=null) {
            // some task that involves getActivity(), use activity instead
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        notify("onPause");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        notify("onAttach");
        this.activity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        notify("onDetach");
    }

    private void notify(String methodName) {
        Log.d("FRAGMENT", methodName);
    }

}
