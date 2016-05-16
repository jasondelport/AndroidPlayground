package com.jasondelport.playground.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AboutFragment extends BaseFragment {

    @BindView(R.id.programmer)
    TextView programmer;
    @BindView(R.id.git_revision)
    TextView githubRevsion;
    @BindView(R.id.github_address)
    TextView githubAddress;
    @BindView(R.id.build_time)
    TextView buildTime;

    private Unbinder unbinder;

    public static Fragment newInstance() {
        Fragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);
        this.setMenuVisibility(false);
        this.setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);

        programmer.setText("Jason Delport");
        githubAddress.setText("Github");
        githubRevsion.setText("Github Revision -> " + getResources().getString(R.string.git_revision));
        buildTime.setText("Build Time -> " + getResources().getString(R.string.build_time));
        return view;
    }

    @OnClick(R.id.programmer)
    public void programmerOnClick(View view) {
        String url = "http://jasondelport.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.github_address)
    public void githubOnClick(View view) {
        String url = "http://github.com/jasondelport";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
