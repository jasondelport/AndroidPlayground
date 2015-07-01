package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jasondelport.notes.Constants;
import com.jasondelport.notes.R;
import com.jasondelport.notes.listener.OnBackPressedListener;
import com.jasondelport.notes.ui.activity.DrawerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DrawerContentFragment extends BaseFragment implements OnBackPressedListener {

    private static boolean shouldInterceptHomeAndBackEvents;

    @Bind(R.id.text)
    TextView text;

    public DrawerContentFragment() {

    }

    public static DrawerContentFragment newInstance(String page) {
        DrawerContentFragment fragment = new DrawerContentFragment();
        Bundle args = new Bundle();
        args.putString("page", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_content, container, false);
        ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        text.setText(getArguments().getString("page"));

        return view;
    }

    @OnClick(R.id.button)
    void loadPage() {
        ((DrawerActivity) getActivity()).loadContent(Constants.FRAGMENT_CONTENT_3);
    }

    @OnClick(R.id.checkbox)
    void changeBoolean(View view) {
        if (((CheckBox) view).isChecked()) {
            shouldInterceptHomeAndBackEvents = true;
        } else {
            shouldInterceptHomeAndBackEvents = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (shouldInterceptHomeAndBackEvents) {
                    ((DrawerActivity) getActivity()).getDrawerLayout().closeDrawers();
                    Snackbar.make(getView(), "Menu Intercepted", Snackbar.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerActivity) getActivity()).setOnBackPressedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((DrawerActivity) getActivity()).setOnBackPressedListener(null);
    }

    @Override
    public void onBackPressed() {
        if (shouldInterceptHomeAndBackEvents) {
            Snackbar.make(getView(), "Back Intercepted", Snackbar.LENGTH_LONG).show();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
