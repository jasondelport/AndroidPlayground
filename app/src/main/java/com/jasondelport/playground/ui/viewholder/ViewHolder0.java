package com.jasondelport.playground.ui.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.playground.R;
import com.jasondelport.playground.data.model.Note;
import com.jasondelport.playground.ui.dialog.ConfirmDeleteDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ViewHolder0<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.note)
    TextView mTextView;
    private Note note;

    public ViewHolder0(View view) {
        super(view);
        view.setOnClickListener(this);
        ButterKnife.bind(this, view);
    }

    public static ViewHolder0 newInstance(ViewGroup parent) {
        return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview, parent, false));
    }

    // pass in context in case we need Picasso
    public void bindDataToView(Context context, T data) {
        this.note = (Note) data;
        mTextView.setText(note.getText());
    }

    @Override
    public void onClick(View view) {
        ConfirmDeleteDialogFragment.newInstance(note.getKey()).show(((Activity) view.getContext()).getFragmentManager(), "dialog");
    }
}