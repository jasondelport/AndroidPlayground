package com.jasondelport.playground.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.jasondelport.playground.Constants;
import com.jasondelport.playground.ui.fragment.MvpFragment;
import com.jasondelport.playground.util.NavUtils;


public class MvpActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragment = MvpFragment.newInstance();
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        }

        NavUtils.addFragment(getFragmentManager(), fragment, android.R.id.content, Constants.FRAGMENT_MVP);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
    }


}
