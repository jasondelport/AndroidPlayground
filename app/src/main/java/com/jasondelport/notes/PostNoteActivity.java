package com.jasondelport.notes;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jasondelport.notes.model.Note;
import com.jasondelport.notes.network.NetworkClient;
import com.jasondelport.notes.util.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class PostNoteActivity extends ActionBarActivity {

    private final static int RESULT_SPEECH = 1;

    @InjectView(R.id.note)
    EditText note;

    @InjectView(R.id.button)
    Button button;

    @InjectView(R.id.speechtotext_button)
    Button button1;

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

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-ZA");
                startActivityForResult(intent, RESULT_SPEECH);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    note.setText(text.get(0));
                }
                break;
            }

        }
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