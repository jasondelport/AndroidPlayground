package com.jasondelport.notes.presenter;

/**
 * Created by jasondelport on 16/07/2015.
 */
public interface MvpView {

    void hideProgress();

    void setItems(String[] countries);

    void showMessage(String message);
}
