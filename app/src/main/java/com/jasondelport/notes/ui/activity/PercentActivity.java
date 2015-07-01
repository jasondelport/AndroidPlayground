package com.jasondelport.notes.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.jasondelport.notes.Constants;
import com.jasondelport.notes.ui.fragment.PercentFragment;
import com.jasondelport.notes.util.NavUtils;


public class PercentActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragment = PercentFragment.newInstance();
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        }

        NavUtils.addFragment(getFragmentManager(), fragment, android.R.id.content, Constants.FRAGMENT_PERCENT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
    }
}
