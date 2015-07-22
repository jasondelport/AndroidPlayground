package com.jasondelport.notes.ui.adapter;

public class AdapterItem<T> {
    public static final int ITEM0 = 0;
    public static final int ITEM2 = 2;
    private int viewType;
    private T object;

    public AdapterItem(T object, int viewType) {
        this.object = object;
        this.viewType = viewType;
    }

    public T getObject() {
        return object;
    }

    public int getViewType() {
        return viewType;
    }

}