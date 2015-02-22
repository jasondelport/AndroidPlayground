package com.jasondelport.notes;

import javax.inject.Singleton;

/**
 * Created by jasondelport on 22/02/2015.
 */
@Singleton
public class MainFragmentState {
    private static final MainFragmentState sInstance;

    private Note note;

    static {
        sInstance = new MainFragmentState();
    }

    private MainFragmentState() {
    }

    public static MainFragmentState getInstance() {
        return sInstance;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
