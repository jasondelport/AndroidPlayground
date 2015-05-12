package com.jasondelport.notes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;


public class MainFragment extends Fragment {

    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.main_button_drawer)
    Button button1;
    @InjectView(R.id.main_button_recyclerview)
    Button button2;
    @InjectView(R.id.main_button_location)
    Button button3;
    @InjectView(R.id.main_button_postnote)
    Button button4;
    private String value1;
    private int value2;
    private OnEventListener listener;

    public MainFragment() {
        lifecycle("constructor");
    }

    // the recommended google way of instantiating new fragments via a static factory method
    public static Fragment newInstance(String value1, int value2) {
        Fragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("value1", value1);
        args.putInt("value2", value2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // skips this lifecycle method and onDestroy when the fragment gets brought to the fore
        setHasOptionsMenu(true);
        lifecycle("onCreate");
        if (getArguments() != null) {
            value1 = getArguments().getString("value1", "default value");
            value2 = getArguments().getInt("value2", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lifecycle("onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        // setup view data here
        text.setText(BuildConfig.URL);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostNoteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifecycle("onActivityCreated");
        // first time the fragment may have been created
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");
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
        if (activity instanceof MainActivity) {
            listener = (OnEventListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifecycle("onDetach");
    }

    // communicate with the activity via a listener
    private void doSomeThing(String data) {
        listener.onEvent(data);
    }

    private void lifecycle(String methodName) {
        Timber.d("Fragment (%s)", methodName);
    }

    public interface OnEventListener {
        public void onEvent(String data);
    }

}
