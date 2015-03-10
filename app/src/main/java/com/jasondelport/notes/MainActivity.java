package com.jasondelport.notes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        lifecycle("onOptionsItemSelected");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        lifecycle("onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        lifecycle("onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_activity_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        Timber.d("Activity (%s)", methodName);
    }
}
