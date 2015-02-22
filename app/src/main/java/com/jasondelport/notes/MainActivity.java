package com.jasondelport.notes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {

    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notify("onCreate");

        if (savedInstanceState != null) {
            fragment = (MainFragment) getFragmentManager().getFragment(savedInstanceState, Constants.MAIN_FRAGMENT_TAG);
        } else {
            fragment = (MainFragment) getFragmentManager().findFragmentByTag(Constants.MAIN_FRAGMENT_TAG);
        }

        if (fragment == null) {
            Log.d("ACTIVITY", "Fragment is null.");
            fragment = new MainFragment();
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment, Constants.MAIN_FRAGMENT_TAG).commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        notify("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        notify("onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        notify("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notify("onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        notify("onRestoreInstanceState");
        fragment = (MainFragment) getFragmentManager().getFragment(savedInstanceState, Constants.MAIN_FRAGMENT_TAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        notify("onSaveInstanceState");
        getFragmentManager().putFragment(outState, "mContent", fragment);
    }

    private void notify(String methodName) {
        Log.d("ACTIVITY", methodName);
    }
}
