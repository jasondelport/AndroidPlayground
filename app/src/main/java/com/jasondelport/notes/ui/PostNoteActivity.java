package com.jasondelport.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.jasondelport.notes.R;
import com.jasondelport.notes.data.DataManager;
import com.jasondelport.notes.model.Note;
import com.jasondelport.notes.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class PostNoteActivity extends BaseActivity {

    private final static int RESULT_SPEECH = 1;

    @Bind(R.id.note)
    EditText note;

    @Bind(R.id.button)
    Button button;

    @Bind(R.id.speechtotext_button)
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnote);
        ButterKnife.bind(this);

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

        int flags = getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) != 0) {

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

                    Calendar calendar = Calendar.getInstance();
                    if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                    }
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

            DataManager.getInstance().addNote(nn, new Callback<Note>() {
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