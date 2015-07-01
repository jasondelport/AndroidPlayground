package com.jasondelport.notes.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.jasondelport.notes.Constants;
import com.jasondelport.notes.ui.fragment.RXJavaFragment;
import com.jasondelport.notes.util.NavUtils;

public class RXJavaActivity extends BaseActivity {

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

        NavUtils.addFragment(getFragmentManager(), fragment, android.R.id.content, Constants.FRAGMENT_RXJAVA);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
    }
}
