package com.jasondelport.notes;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.model.Note;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;


public class MainFragment extends Fragment {

    private Activity activity;

    private MainFragmentState state = MainFragmentState.getInstance();

    @InjectView(R.id.text)
    TextView text;


    public MainFragment() {
        lifecycle("constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // skips this lifecycle method and onDestroy when the fragment gets brought to the fore
        setHasOptionsMenu(true);
        lifecycle("onCreate");
        // initialise data here
        state.setNote(new Note());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lifecycle("onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        // initialise view here
        text.setText(BuildConfig.URL);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifecycle("onActivityCreated");
        if (savedInstanceState != null) {
            Note note = Parcels.unwrap(savedInstanceState.getParcelable("note"));
            state.setNote(note);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");
        Parcelable wrappedNote = Parcels.wrap(state.getNote());
        outState.putParcelable("note", wrappedNote);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle("onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycle("onResume");

        if (isAdded() || activity!=null) {
            // some task that involves getActivity(), use activity instead
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        lifecycle("onOptionsItemSelected");
        inflater.inflate(R.menu.menu_fragment_main, menu);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        lifecycle("onPrepareOptionsMenu");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        lifecycle("onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_fragment_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycle("onPause");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        lifecycle("onAttach");
        this.activity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifecycle("onDetach");
    }

    private void lifecycle(String methodName) {
        Timber.d("Fragment (%s)", methodName);
    }

}
