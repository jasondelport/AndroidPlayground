package com.jasondelport.notes.ui.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.R;
import com.jasondelport.notes.data.model.Note;
import com.jasondelport.notes.ui.dialog.ConfirmDeleteDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewHolder2<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.note)
    TextView mTextView;
    private Note note;

    public ViewHolder2(View view) {
        super(view);
        view.setOnClickListener(this);
        ButterKnife.bind(this, view);
    }

    public static ViewHolder2 newInstance(ViewGroup parent) {
        return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview_alternate, parent, false));
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