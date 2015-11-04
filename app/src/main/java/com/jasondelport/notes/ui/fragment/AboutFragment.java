package com.jasondelport.notes.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AboutFragment extends BaseFragment {

    @Bind(R.id.programmer)
    TextView programmer;
    @Bind(R.id.git_revision)
    TextView githubRevsion;
    @Bind(R.id.github_address)
    TextView githubAddress;

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
        ButterKnife.bind(this, view);

        programmer.setText("Jason Delport");
        githubAddress.setText("Github");
        githubRevsion.setText("Github Revsion -> " + getResources().getString(R.string.git_revision));

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

}
