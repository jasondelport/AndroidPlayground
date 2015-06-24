package com.jasondelport.notes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class RXJavaActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragment = RXJavaFragment.newInstance();
            fragment.setArguments(getIntent().getExtras());
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        }

        getFragmentManager().beginTransaction().add(android.R.id.content, fragment, Constants.RXJAVA_FRAGMENT_TAG).commit();

    }
}
