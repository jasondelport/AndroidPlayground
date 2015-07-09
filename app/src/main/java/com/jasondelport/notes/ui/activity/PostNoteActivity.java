package com.jasondelport.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.EditText;

import com.jasondelport.notes.R;
import com.jasondelport.notes.data.model.Note;
import com.jasondelport.notes.data.server.DataManager;
import com.jasondelport.notes.util.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class PostNoteActivity extends BaseActivity {

    private final static int RESULT_SPEECH = 1;
    private DataManager dataManager = new DataManager();

    @Bind(R.id.note)
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnote);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.speechtotext_button)
    void speechRecogniser() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-ZA");
        startActivityForResult(intent, RESULT_SPEECH);
    }

    @OnClick(R.id.button)
    void postNote() {
        if (!Utils.isEmptyString(note.getText().toString())) {
            Note nn = new Note();
            nn.setNote(note.getText().toString());

            dataManager.getRestApi().addNote(nn, new Callback<Note>() {
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
}