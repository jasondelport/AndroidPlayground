package com.jasondelport.playground.ui.activity;

import android.app.Activity;
import android.os.Bundle;


public class ThrowExceptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        throw new RuntimeException("Error occurred!");

    }


}
