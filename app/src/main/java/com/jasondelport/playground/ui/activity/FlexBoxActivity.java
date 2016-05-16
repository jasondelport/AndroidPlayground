package com.jasondelport.playground.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.jasondelport.playground.Constants;
import com.jasondelport.playground.ui.fragment.FlexBoxFragment;
import com.jasondelport.playground.util.NavUtils;

import timber.log.Timber;

public class FlexBoxActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");

        if (savedInstanceState == null) {
            fragment = FlexBoxFragment.newInstance();
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "FlexBoxFragment");
            if (fragment == null) {
                fragment = FlexBoxFragment.newInstance();
            }
        }

        NavUtils.addFragment(getFragmentManager(), fragment, android.R.id.content, Constants.FRAGMENT_FLEXBOX);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");
        getFragmentManager().putFragment(outState, "FlexBoxFragment", fragment);
    }

    private void lifecycle(String methodName) {
        Timber.d("Activity (%s)", methodName);
    }
}
