package com.jasondelport.playground.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jasondelport.playground.BuildConfig;
import com.jasondelport.playground.Constants;
import com.jasondelport.playground.R;
import com.jasondelport.playground.data.singleton.ExampleSingleton;
import com.jasondelport.playground.ui.fragment.MainFragment;
import com.jasondelport.playground.util.DebugUtils;
import com.jasondelport.playground.util.NavUtils;

import hugo.weaving.DebugLog;
import timber.log.Timber;


public class MainActivity extends BaseActivity implements MainFragment.OnEventListener {
    /*

    lifecycle order of a activity with a child fragment

    start
    Activity (onCreate)
    Fragment (constructor)
    Fragment (onAttach)
    Fragment (onCreate)
    Fragment (onCreateView)
    Fragment (onActivityCreated)
    Activity (onStart)
    Fragment (onStart)
    Activity (onResume)
    Fragment (onResume)

    stop (with setRetainInstance = true)
    Fragment (onPause)
    Activity (onPause)
    Fragment (onSaveInstanceState)
    Activity (onSaveInstanceState)
    Fragment (onStop)
    Activity (onStop)
    Fragment (onDestroyView)
    Fragment (onDetach)
    Activity (onDestroy)

    stop (with setRetainInstance = false)
    Fragment (onPause)
    Activity (onPause)
    Fragment (onSaveInstanceState)
    Activity (onSaveInstanceState)
    Fragment (onStop)
    Activity (onStop)
    Fragment (onDestroyView)
    Fragment (onDestroy) **
    Fragment (onDetach)
    Activity (onDestroy)

    restart (with setRetainInstance = true)
    Fragment (onAttach)
    Activity (onCreate)
    Fragment (onCreateView)
    Fragment (onActivityCreated)
    Activity (onStart)
    Fragment (onStart)
    MainActivity (onRestoreInstanceState)
    MainActivity (onResume)
    Fragment (onResume)

    restart (with setRetainInstance = false)
    Fragment (onAttach)
    Fragment (onCreate) **
    Activity (onCreate)
    Fragment (onCreateView)
    Fragment (onActivityCreated)
    Activity (onStart)
    Fragment (onStart)
    MainActivity (onRestoreInstanceState)
    MainActivity (onResume)
    Fragment (onResume)

     */

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");

        if (BuildConfig.DEBUG) {
            DebugUtils.riseAndShine(this);
        }

        if (savedInstanceState == null) {
            fragment = MainFragment.newInstance("hello", 0);
            // the below overwrites the above
            //fragment.setArguments(getIntent().getExtras());
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "MainFragment");
        }

        NavUtils.addFragment(getFragmentManager(), fragment, android.R.id.content, Constants.FRAGMENT_MAIN);

        addShortCut();

        // testing the example singleton
        ExampleSingleton.getInstance().setHelloWorld("Hello World");
        Timber.d(ExampleSingleton.getInstance().getHelloWorld());


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

    /*
    The action bar will automatically handle clicks on the Home/Up button,
    so long as you specify a parent activity in AndroidManifest.xml.
     */
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
    public void onUserInteraction() {
        super.onUserInteraction();
        lifecycle("onUserInteraction");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lifecycle("onNewIntent");
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");
        getFragmentManager().putFragment(outState, "MainFragment", fragment);
    }

    private void lifecycle(String methodName) {
        Timber.d("Activity -> %s", methodName);
    }

    @Override
    public void onEvent(String data) {
        // listener method from fragment
    }

    @DebugLog
    private void addShortCut() {
        Intent shortcutIntent = new Intent(getApplicationContext(), PostNoteActivity.class);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Note");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));
        addIntent.putExtra("duplicate", false); // doesn't work properly
        getApplicationContext().sendBroadcast(addIntent);

        addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }


}