package com.jasondelport.notes;

import org.parceler.Parcel;

/**
 * Created by jasondelport on 22/02/2015.
 */
@Parcel
public class Note {
    private String key;
    private long added;
    private long updated;
    private String text;

    public Note() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
