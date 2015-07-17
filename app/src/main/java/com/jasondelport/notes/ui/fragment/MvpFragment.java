package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jasondelport.notes.R;
import com.jasondelport.notes.presenter.MvpPresenter;
import com.jasondelport.notes.presenter.MvpView;

import java.util.Arrays;

import butterknife.ButterKnife;


public class MvpFragment extends BaseFragment implements MvpView, AdapterView.OnItemClickListener {

    private ListView listView;
    private ProgressBar progressBar;
    private MvpPresenter presenter;

    public MvpFragment() {

    }

    public static MvpFragment newInstance() {
        MvpFragment fragment = new MvpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new MvpPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mvp, container, false);
        ButterKnife.bind(this, view);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(String[] countries) {
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Arrays.asList(countries));
        listView.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onItemClicked(position);
    }
}
