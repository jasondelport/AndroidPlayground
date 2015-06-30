package com.jasondelport.notes.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.jasondelport.notes.Constants;
import com.jasondelport.notes.ui.fragment.PercentFragment;
import com.jasondelport.notes.util.RoutingUtils;


public class PercentActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragment = PercentFragment.newInstance();
            fragment.setArguments(getIntent().getExtras());
            RoutingUtils.addFragment(PercentActivity.this, fragment, android.R.id.content, Constants.PERCENT_FRAGMENT_TAG);
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
            RoutingUtils.addFragment(PercentActivity.this, fragment, android.R.id.content, Constants.PERCENT_FRAGMENT_TAG);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
    }
}
