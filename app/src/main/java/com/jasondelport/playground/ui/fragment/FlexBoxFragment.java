package com.jasondelport.playground.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasondelport.playground.R;

import timber.log.Timber;


public class FlexBoxFragment extends BaseFragment {

    public FlexBoxFragment() {

    }

    //@DebugLog
    public static FlexBoxFragment newInstance() {
        FlexBoxFragment fragment = new FlexBoxFragment();
        return fragment;
    }

    //@DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");
        setRetainInstance(false);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lifecycle("onCreateView");
        // https://github.com/google/flexbox-layout
        View view = inflater.inflate(R.layout.fragment_flexbox, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        lifecycle("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycle("onDestroy");
        super.onDestroy();
    }

    private void lifecycle(String methodName) {
        Timber.d("Fragment (%s)", methodName);
    }

}
