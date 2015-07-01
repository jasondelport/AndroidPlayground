package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BlankFragment extends BaseFragment {

    @Bind(R.id.text)
    TextView text;

    public BlankFragment() {

    }

    public static BlankFragment newInstance(String page) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("page", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(false);
        setHasOptionsMenu(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, view);

        text.setText(getArguments().getString("page"));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
