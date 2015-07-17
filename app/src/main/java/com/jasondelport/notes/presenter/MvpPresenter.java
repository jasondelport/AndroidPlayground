package com.jasondelport.notes.presenter;

import com.jasondelport.notes.data.local.DemoData;
import com.jasondelport.notes.listener.OnFinishedListener;

/**
 * Created by jasondelport on 16/07/2015.
 */
public class MvpPresenter implements OnFinishedListener {
    private MvpView mvpView;
    private DemoData data;
    private String[] countries;

    public MvpPresenter(MvpView mvpView) {
        this.mvpView = mvpView;
        data = new DemoData();
        data.getCountries(this);
    }

    public void onResume() {
        if (countries != null) {
            onFinished(countries);
        }
    }

    public void onItemClicked(int position) {
        mvpView.showMessage(String.format("Position %d clicked", position + 1));
    }

    @Override
    public void onFinished(String[] countries) {
        this.countries = countries;
        mvpView.setItems(countries);
        mvpView.hideProgress();
    }
}
