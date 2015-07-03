package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import timber.log.Timber;


public class DrawerContentChildFragment extends BaseFragment implements OnBackPressedListener {

    @Bind(R.id.text)
    TextView text;
    boolean shouldInterceptHomeAndBackEvents;
    private String page;

    public DrawerContentChildFragment() {

    }

    public static DrawerContentChildFragment newInstance(String page) {
        DrawerContentChildFragment fragment = new DrawerContentChildFragment();
        Bundle args = new Bundle();
        args.putString("page", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(false);
        this.setHasOptionsMenu(true);
        page = getArguments().getString("page");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            shouldInterceptHomeAndBackEvents = savedInstanceState.getBoolean("shouldInterceptHomeAndBackEvents");
        }

        View view = inflater.inflate(R.layout.fragment_drawer_content_child, container, false);
        ButterKnife.bind(this, view);

        /*
        this doesn't resolve the onOptionsItemSelected issue. i.e. it's not Butterknife's fault

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    shouldInterceptHomeAndBackEvents = true;
                    ((DrawerContentParentFragment) getParentFragment()).setOnBackPressedListener(DrawerContentChildFragment.this);
                } else {
                    shouldInterceptHomeAndBackEvents = false;
                    ((DrawerContentParentFragment) getParentFragment()).setOnBackPressedListener(null);
                }
            }
        });
        */

        text.setText(page);

        return view;
    }

    @OnClick(R.id.button)
    void loadPage() {
        ((DrawerActivity) getParentFragment().getActivity()).loadContent(Constants.FRAGMENT_CONTENT_3);
    }

    @OnClick(R.id.checkbox)
    void changeBoolean(View view) {
        if (((CheckBox) view).isChecked()) {
            shouldInterceptHomeAndBackEvents = true;
            ((DrawerContentParentFragment) getParentFragment()).setOnBackPressedListener(this);
        } else {
            shouldInterceptHomeAndBackEvents = false;
            ((DrawerContentParentFragment) getParentFragment()).setOnBackPressedListener(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // the below doesn't work, not sure why, grrrr!, the boolean is always false even when changed else where
                // it's got something to do with this specific method as onbackpressed works
                Timber.d("get -> %b", shouldInterceptHomeAndBackEvents);
                if (shouldInterceptHomeAndBackEvents) {
                    ((DrawerActivity) getParentFragment().getActivity()).getDrawerLayout().closeDrawers();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("shouldInterceptHomeAndBackEvents", shouldInterceptHomeAndBackEvents);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shouldInterceptHomeAndBackEvents) {
            ((DrawerContentParentFragment) getParentFragment()).setOnBackPressedListener(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((DrawerContentParentFragment) getParentFragment()).setOnBackPressedListener(null);
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
