package com.jasondelport.notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String[] mPlanetTitles;

    public static class ViewHolder0 extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder0(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder2(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
        }
    }

    public RecyclerViewAdapter(String[] mPlanetTitles) {
        this.mPlanetTitles = mPlanetTitles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
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
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 h0 = (ViewHolder0) holder;
                h0.mTextView.setText(mPlanetTitles[position]);
            default:
                ViewHolder2 h2 = (ViewHolder2) holder;
                h2.mTextView.setText(mPlanetTitles[position]);
        }
    }

    @Override
    public int getItemCount() {
        return mPlanetTitles.length;
    }
}