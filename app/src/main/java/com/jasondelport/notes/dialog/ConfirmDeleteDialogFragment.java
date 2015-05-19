package com.jasondelport.notes.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.jasondelport.notes.model.ServerResponse;
import com.jasondelport.notes.network.NetworkClient;
import com.jasondelport.notes.network.OttoCallback;

/**
 * Created by jasondelport on 19/05/15.
 */
public class ConfirmDeleteDialogFragment extends DialogFragment {

    public static ConfirmDeleteDialogFragment newInstance(String key) {
        ConfirmDeleteDialogFragment f = new ConfirmDeleteDialogFragment();
        Bundle args = new Bundle();
        args.putString("key", key);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Confirmation Required")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkClient.getService().deleteNote(getArguments().getString("key"), new OttoCallback<ServerResponse>());
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();
    }
}