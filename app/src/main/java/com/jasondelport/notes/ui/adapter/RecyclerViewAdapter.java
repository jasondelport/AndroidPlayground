package com.jasondelport.notes.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jasondelport.notes.data.model.Note;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Note> notes;

    public RecyclerViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    /*
    public void addNote(Note note){
        notes.add(new AdapterItem<>(note, AdapterItem.ITEM0));
        notifyItemInserted(notes.size());
    }
    */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemViewType.VIEWTYPE0:
                return ViewHolder0.newInstance(parent);
            default:
                return ViewHolder2.newInstance(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case ItemViewType.VIEWTYPE0:
                ((ViewHolder0) holder).setNote(notes.get(position));
                break;
            default:
                ((ViewHolder2) holder).setNote(notes.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

}