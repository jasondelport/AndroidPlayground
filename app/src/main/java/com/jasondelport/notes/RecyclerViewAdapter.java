package com.jasondelport.notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.model.Note;
import com.jasondelport.notes.util.Utils;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Note> mNotes;

    public class ItemViewType {
        private static final int VIEWTYPE0 = 0, VIEWTYPE2 = 1;
    }

    public static class ViewHolder0 extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder0(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.note);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.makeToast(v.getContext(), "clicked");
                }
            });
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder2(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.note);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.makeToast(v.getContext(), "clicked");
                }
            });
        }
    }

    public RecyclerViewAdapter(List<Note> mNotes) {
        this.mNotes = mNotes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemViewType.VIEWTYPE0:
                View v0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview, parent, false);
                ViewHolder0 vh0 = new ViewHolder0(v0);
                return vh0;
            default:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview_alternate, parent, false);
                ViewHolder2 vh2 = new ViewHolder2(v2);
                return vh2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Timber.d("type -> %d", getItemViewType(position));
        switch (getItemViewType(position)) {
            case ItemViewType.VIEWTYPE0:
                ViewHolder0 h0 = (ViewHolder0) holder;
                h0.mTextView.setText(mNotes.get(position).getText());
                break;
            default:
                ViewHolder2 h2 = (ViewHolder2) holder;
                h2.mTextView.setText(mNotes.get(position).getText());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


}