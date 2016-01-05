package com.jasondelport.playground.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jasondelport.playground.data.model.Note;
import com.jasondelport.playground.ui.viewholder.ViewHolder0;
import com.jasondelport.playground.ui.viewholder.ViewHolder2;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Note> mNotes;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Note> notes) {
        this.mContext = context;
        this.mNotes = notes;
    }

    /*
    public void addNote(Note note){
        this.mNotes.add(new AdapterItem<>(note, AdapterItem.ITEM0));
        notifyItemInserted(notes.size());
    }

    public void addNotes(List<Note> notes){
        int currentSize = mNotes.size();
        int amountInserted = notes.size();
        this.mNotes.addAll(notes);
        notifyItemRangeInserted(currentSize, amountInserted);
    }
    */

    public List<Note> getNotes() {
        return mNotes;
    }

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
                ((ViewHolder0) holder).bindDataToView(mContext, mNotes.get(position));
                break;
            default:
                ((ViewHolder2) holder).bindDataToView(mContext, mNotes.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

}