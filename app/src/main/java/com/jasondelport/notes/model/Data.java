package com.jasondelport.notes.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by jasondelport on 08/04/15.
 */
@Parcel
public class Data {

    @SerializedName("notes")
    List<Note> notes;

    public Data(){}

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
