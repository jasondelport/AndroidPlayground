package com.jasondelport.notes.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jasondelport.notes.R;
import com.jasondelport.notes.ui.activity.BleActivity;
import com.jasondelport.notes.ui.activity.CoordinatorLayoutActivity;
import com.jasondelport.notes.ui.activity.DrawerActivity;
import com.jasondelport.notes.ui.activity.KeepAwakeActivity;
import com.jasondelport.notes.ui.activity.LocationActivity;
import com.jasondelport.notes.ui.activity.MainActivity;
import com.jasondelport.notes.ui.activity.PercentActivity;
import com.jasondelport.notes.ui.activity.PostNoteActivity;
import com.jasondelport.notes.ui.activity.RXJavaActivity;
import com.jasondelport.notes.ui.activity.RecyclerViewActivity;
import com.jasondelport.notes.util.NavUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


public class MainFragment extends BaseFragment {

    private String value1;
    private int value2;
    private OnEventListener listener;

    public MainFragment() {
        lifecycle("constructor");
    }

    // the recommended google way of instantiating new fragments via a static factory method
    public static Fragment newInstance(String value1, int value2) {
        Timber.d("newInstance");
        Fragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("value1", value1);
        args.putInt("value2", value2);
        fragment.setArguments(args);
        return fragment;
    }

    /*
    // alternate way of instantiating the fragment statically
    public static Fragment newInstance(Context context, String value1, int value2) {
        Bundle args = new Bundle();
        args.putString("value1", value1);
        args.putInt("value2", value2);
        return Fragment.instantiate(context, MainFragment.class.getName(), args);
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");
        setRetainInstance(false); // skips this lifecycle method and onDestroy when the fragment gets brought to the fore
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            value1 = getArguments().getString("value1", "default value");
            value2 = getArguments().getInt("value2", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lifecycle("onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.main_button_drawer)
    void loadDrawer(View view) {
        NavUtils.openActivity(getActivity(), DrawerActivity.class);
    }

    @OnClick(R.id.main_button_recyclerview)
    void loadRecyclerView(View view) {
        NavUtils.openActivity(getActivity(), RecyclerViewActivity.class);
    }

    @OnClick(R.id.main_button_location)
    void loadLocation(View view) {
        NavUtils.openActivity(getActivity(), LocationActivity.class);
    }

    @OnClick(R.id.main_button_postnote)
    void loadPostNote(View view) {
        NavUtils.openActivity(getActivity(), PostNoteActivity.class);
    }

    @OnClick(R.id.main_button_keep_awake)
    void loadKeepAwake(View view) {
        NavUtils.openActivity(getActivity(), KeepAwakeActivity.class);
    }

    @OnClick(R.id.main_button_rxjava)
    void loadRXJava(View view) {
        NavUtils.openActivity(getActivity(), RXJavaActivity.class);
    }

    @OnClick(R.id.main_button_percents)
    void loadPercents(View view) {
        NavUtils.openActivity(getActivity(), PercentActivity.class);
    }

    @OnClick(R.id.main_button_coordinator)
    void loadCoordinator(View view) {
        NavUtils.openActivity(getActivity(), CoordinatorLayoutActivity.class);
    }

    @OnClick(R.id.main_button_ble)
    void loadBleScanner(View view) {
        NavUtils.openActivity(getActivity(), BleActivity.class);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifecycle("onActivityCreated");
        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");

        //Save the fragment's state here
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
    public void onDestroyView() {
        super.onDestroyView();
        lifecycle("onDestroyView");
        ButterKnife.unbind(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle("onDestroy");
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
