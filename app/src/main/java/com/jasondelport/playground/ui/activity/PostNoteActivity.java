package com.jasondelport.playground.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.EditText;

import com.jasondelport.playground.R;
import com.jasondelport.playground.data.model.Note;
import com.jasondelport.playground.data.server.DataService;
import com.jasondelport.playground.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PostNoteActivity extends BaseActivity {

    private final static int RESULT_SPEECH = 1;
    private DataService dataManager = new DataService();

    @BindView(R.id.note)
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

            Call<Note> call = dataManager.getRestApi().addNote(nn);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    Timber.d("new note -> %s", response.body().getText());
                    finish();
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Timber.d("error -> %s", t.toString());
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