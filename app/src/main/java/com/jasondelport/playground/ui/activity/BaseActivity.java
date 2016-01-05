package com.jasondelport.playground.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");
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
    }

    private void lifecycle(String methodName) {
        //Timber.d("%s (%s)", getClass().getSimpleName(), methodName);
    }
}
