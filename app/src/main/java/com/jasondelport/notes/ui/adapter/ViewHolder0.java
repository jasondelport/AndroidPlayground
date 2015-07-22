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

public class ViewHolder0 extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mTextView;
    private Note note;

    public ViewHolder0(View view) {
        super(view);
        view.setOnClickListener(this);
        mTextView = (TextView) view.findViewById(R.id.note);
    }

    public static ViewHolder0 newInstance(ViewGroup parent) {
        return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview, parent, false));
    }

    public void setNote(Note note) {
        this.note = note;
        mTextView.setText(note.getText());
    }

    @Override
    public void onClick(View view) {
        ConfirmDeleteDialogFragment.newInstance(note.getKey()).show(((Activity) view.getContext()).getFragmentManager(), "dialog");
    }
}