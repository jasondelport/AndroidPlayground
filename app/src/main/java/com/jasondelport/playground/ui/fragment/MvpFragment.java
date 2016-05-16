package com.jasondelport.playground.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jasondelport.playground.R;
import com.jasondelport.playground.presenter.MvpPresenter;
import com.jasondelport.playground.presenter.MvpPresenterImpl;
import com.jasondelport.playground.ui.view.MvpView;

import java.util.Arrays;


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
        presenter = new MvpPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mvp, container, false);

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDetach() {
        presenter = null;
        super.onDetach();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(String[] countries) {
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Arrays.asList(countries)));
    }

    @Override
    public void showMessage(String message) {
        final Snackbar snackBar = Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        };
        snackBar.setAction("Dismiss", listener);
        snackBar.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onItemClicked(position);
    }
}
