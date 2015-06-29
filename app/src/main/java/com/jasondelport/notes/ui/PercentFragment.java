package com.jasondelport.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasondelport.notes.R;


public class PercentFragment extends BaseFragment {

    public PercentFragment() {

    }

    public static PercentFragment newInstance() {
        PercentFragment fragment = new PercentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_percent, container, false);

        return view;
    }

}
