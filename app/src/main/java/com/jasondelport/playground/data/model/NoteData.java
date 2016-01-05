package com.jasondelport.playground.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by jasondelport on 08/04/15.
 */
@Parcel
public class NoteData {

    @SerializedName("notes")
    List<Note> notes;

    public NoteData() {
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
