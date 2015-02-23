package com.jasondelport.notes;

import android.app.Activity;
import android.os.Bundle;

import timber.log.Timber;


public class MainActivity extends Activity {

    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");

//        NotesClient client = new NotesClient();
//        client.getNotes();

        if (savedInstanceState == null) {
            fragment = new MainFragment();
            if (getIntent() != null) {
                fragment.setArguments(getIntent().getExtras());
            }
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment, Constants.MAIN_FRAGMENT_TAG).commit();
        } else {
            fragment = (MainFragment) getFragmentManager().getFragment(savedInstanceState, Constants.MAIN_FRAGMENT_TAG);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycle("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycle("onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycle("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycle("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycle("onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lifecycle("onRestoreInstanceState");
        if (savedInstanceState != null) {
            fragment = (MainFragment) getFragmentManager().getFragment(savedInstanceState, Constants.MAIN_FRAGMENT_TAG);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");
        getFragmentManager().putFragment(outState, Constants.MAIN_FRAGMENT_TAG, fragment);
    }

    private void lifecycle(String methodName) {
        Timber.d("Method Name -> %s", methodName);
    }
}
