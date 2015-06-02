package com.jasondelport.notes.dialog;

import android.app.Activity;
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

    DialogListener mListener;

    public static ConfirmDeleteDialogFragment newInstance(String key) {
        ConfirmDeleteDialogFragment f = new ConfirmDeleteDialogFragment();
        Bundle args = new Bundle();
        args.putString("key", key);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        DialogFragment.STYLE_NO_TITLE;
        DialogFragment.STYLE_NO_FRAME;
        DialogFragment.STYLE_NORMAL;
        DialogFragment.STYLE_NO_INPUT;

        android.R.style.Theme_Holo;
        android.R.style.Theme_Holo_Light_Dialog;
        android.R.style.Theme_Holo_Light;
        android.R.style.Theme_Holo_Light_Panel;
        android.R.style.Theme_Holo_Light;
        */

        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Material_Dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.dialog_signin, null));
        builder.setTitle("Confirmation Required")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkClient.getService().deleteNote(getArguments().getString("key"), new OttoCallback<ServerResponse>());
                        mListener.onConfirmDelete();
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    public interface DialogListener {
        void onConfirmDelete();
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        return view;
    }
    */

}