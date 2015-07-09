package com.jasondelport.notes.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.R;
import com.jasondelport.notes.data.model.Note;
import com.jasondelport.notes.ui.dialog.ConfirmDeleteDialogFragment;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Note> mNotes;

    public RecyclerViewAdapter(List<Note> mNotes) {
        this.mNotes = mNotes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemViewType.VIEWTYPE0:
                View view0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview, parent, false);
                ViewHolder0 vh0 = new ViewHolder0(view0);
                return vh0;
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview_alternate, parent, false);
                ViewHolder2 vh2 = new ViewHolder2(view2);
                return vh2;
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
                ViewHolder0 h0 = (ViewHolder0) holder;
                h0.mTextView.setText(mNotes.get(position).getText());
                h0.setNote(mNotes.get(position));
                break;
            default:
                ViewHolder2 h2 = (ViewHolder2) holder;
                h2.mTextView.setText(mNotes.get(position).getText());
                h2.setNote(mNotes.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public static class ViewHolder0 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        private Note note;

        public ViewHolder0(View view) {
            super(view);
            view.setOnClickListener(this);
            mTextView = (TextView) view.findViewById(R.id.note);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        @Override
        public void onClick(View view) {
            ConfirmDeleteDialogFragment.newInstance(note.getKey()).show(((Activity) view.getContext()).getFragmentManager(), "dialog");
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        private Note note;

        public ViewHolder2(View view) {
            super(view);
            view.setOnClickListener(this);
            mTextView = (TextView) view.findViewById(R.id.note);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        @Override
        public void onClick(View view) {
            ConfirmDeleteDialogFragment.newInstance(note.getKey()).show(((Activity) view.getContext()).getFragmentManager(), "dialog");
        }
    }

    public class ItemViewType {
        private static final int VIEWTYPE0 = 0, VIEWTYPE2 = 1;
    }


}