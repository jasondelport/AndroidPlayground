package com.jasondelport.notes.presenter;

/**
 * Created by jasondelport on 16/07/2015.
 */
public interface MvpView {

    public void showProgress();

    public void hideProgress();

    public void setItems(String[] countries);

    public void showMessage(String message);
}
