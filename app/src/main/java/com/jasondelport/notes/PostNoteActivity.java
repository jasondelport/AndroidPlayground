package com.jasondelport.notes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jasondelport.notes.model.Note;
import com.jasondelport.notes.network.NetworkClient;
import com.jasondelport.notes.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class PostNoteActivity extends FragmentActivity {

    @InjectView(R.id.note)
    EditText note;

    @InjectView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnote);
        ButterKnife.inject(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNote();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void postNote() {
        if (!Utils.isEmptyString(note.getText().toString())) {
            Note nn = new Note();
            nn.setNote(note.getText().toString());

            NetworkClient.getService().addNote(nn, new Callback<Note>() {
                @Override
                public void success(Note note, Response response) {
                    Timber.d("new note -> %s", note.getText());
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    Timber.d("error -> %s", error.toString());
                }
            });
        }
    }

}