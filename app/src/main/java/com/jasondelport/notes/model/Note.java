package com.jasondelport.notes.model;

import org.parceler.Parcel;

/**
 * Created by jasondelport on 23/02/2015.
 */
@Parcel
public class Note {
    private long added;
    private long updated;
    private String key;
    private String text;

    public long getAdded() {
        return added;
    }

    public void setAdded(long added) {
        this.added = added;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
