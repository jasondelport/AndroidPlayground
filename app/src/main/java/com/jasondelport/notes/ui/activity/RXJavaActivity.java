package com.jasondelport.notes.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.jasondelport.notes.Constants;
import com.jasondelport.notes.ui.fragment.RXJavaFragment;
import com.jasondelport.notes.util.NavUtils;

import timber.log.Timber;

public class RXJavaActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");

        if (savedInstanceState == null) {
            fragment = RXJavaFragment.newInstance();
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "RXJavaFragment");
            if (fragment == null) {
                fragment = RXJavaFragment.newInstance();
            }
        }

        NavUtils.addFragment(getFragmentManager(), fragment, android.R.id.content, Constants.FRAGMENT_RXJAVA);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");
        getFragmentManager().putFragment(outState, "RXJavaFragment", fragment);
    }

    private void lifecycle(String methodName) {
        Timber.d("Activity (%s)", methodName);
    }
}
