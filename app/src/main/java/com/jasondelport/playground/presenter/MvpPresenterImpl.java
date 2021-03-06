package com.jasondelport.playground.presenter;

import com.jasondelport.playground.data.local.DemoData;
import com.jasondelport.playground.listener.OnFinishedListener;
import com.jasondelport.playground.ui.view.MvpView;

/**
 * Created by jasondelport on 16/07/2015.
 */
public class MvpPresenterImpl implements MvpPresenter, OnFinishedListener {
    private MvpView mvpView;
    private DemoData data;
    private String[] countries;

    public MvpPresenterImpl(MvpView mvpView) {
        this.mvpView = mvpView;
        data = new DemoData();
        data.getCountries(this);
    }

    @Override
    public void onResume() {
        if (countries != null) {
            mvpView.setItems(countries);
            mvpView.hideProgress();
        }
    }

    @Override
    public void onItemClicked(int position) {
        String country = countries[position];
        mvpView.showMessage(country);
    }

    @Override
    public void onFinished(String[] countries) {
        this.countries = countries;
        mvpView.setItems(countries);
        mvpView.hideProgress();
    }
}
