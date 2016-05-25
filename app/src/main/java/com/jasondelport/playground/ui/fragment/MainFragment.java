package com.jasondelport.playground.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jasondelport.playground.R;
import com.jasondelport.playground.data.model.Money;
import com.jasondelport.playground.ui.activity.AboutActivity;
import com.jasondelport.playground.ui.activity.BluetoothScannerActivity;
import com.jasondelport.playground.ui.activity.CameraActivity;
import com.jasondelport.playground.ui.activity.CoordinatorLayoutActivity;
import com.jasondelport.playground.ui.activity.DetectedActivityActivity;
import com.jasondelport.playground.ui.activity.DrawerActivity;
import com.jasondelport.playground.ui.activity.FlexBoxActivity;
import com.jasondelport.playground.ui.activity.JobSchedulerActivity;
import com.jasondelport.playground.ui.activity.KeepAwakeActivity;
import com.jasondelport.playground.ui.activity.LocationActivity;
import com.jasondelport.playground.ui.activity.MainActivity;
import com.jasondelport.playground.ui.activity.MvpActivity;
import com.jasondelport.playground.ui.activity.PercentActivity;
import com.jasondelport.playground.ui.activity.PostNoteActivity;
import com.jasondelport.playground.ui.activity.RXJavaActivity;
import com.jasondelport.playground.ui.activity.RealmActivity;
import com.jasondelport.playground.ui.activity.RecyclerViewActivity;
import com.jasondelport.playground.ui.activity.SensorActivity;
import com.jasondelport.playground.ui.activity.TelephonyActivity;
import com.jasondelport.playground.ui.activity.ThrowExceptionActivity;
import com.jasondelport.playground.util.NavUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;


public class MainFragment extends BaseFragment {

    private String mString;
    private int mInt;
    private OnEventListener listener;
    private Unbinder unbinder;

    public MainFragment() {
        lifecycle("constructor");
    }

    // the recommended google way of instantiating new fragments via a static factory method
    public static Fragment newInstance(String p1, int p2) {
        Fragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("string", p1);
        args.putInt("int", p2);
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

    /*
    public void setRetainInstance (boolean retain)

    Control whether a fragment instance is retained across Activity re-creation (such as from a configuration change).
    This can only be used with fragments not in the back stack. If set, the fragment lifecycle will be slightly
    different when an activity is recreated:

    onDestroy() will not be called (but onDetach() still will be, because the
    fragment is being detached from its current activity).
    onCreate(Bundle) will not be called since the fragment is not being re-created.
    onAttach(Activity) and onActivityCreated(Bundle) will still be called.
    */

    /*
    Called to do initial creation of a fragment. This is called after onAttach(Activity) and before
    onCreateView(LayoutInflater, ViewGroup, Bundle).

    Note that this can be called while the fragment's activity is still in the process of being created.
    As such, you can not rely on things like the activity's content view hierarchy being initialized
    at this point. If you want to do work once the activity itself is created, see onActivityCreated(Bundle).
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");

        this.setRetainInstance(true);
        this.setMenuVisibility(true);
        this.setHasOptionsMenu(true);

        // auto value example
        Money money = Money.create("Pounds", 522);
        Timber.d(money.toString());


        // if retain instance is set to true these values will persist
        // orientation changes and the death of the parent activity
        if (getArguments() != null) {
            mString = getArguments().getString("string", "default value");
            mInt = getArguments().getInt("int", 0);
        }
    }

    /*
    Called to have the fragment instantiate its user interface view. This is optional, and non-graphical
    fragments can return null (which is the default implementation). This will be called between
    onCreate(Bundle) and onActivityCreated(Bundle).

    If you return a View from here, you will later be called in onDestroyView() when the view is being released.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lifecycle("onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.setDebug(true);
        unbinder = ButterKnife.bind(this, view);

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

    @OnClick(R.id.main_button_flexbox)
    void loadFlexBox() {
        NavUtils.openActivity(getActivity(), FlexBoxActivity.class);
    }

    @OnClick(R.id.main_button_percents)
    void loadPercents() {
        NavUtils.openActivity(getActivity(), PercentActivity.class);
    }

    @OnClick(R.id.main_button_sensors)
    void loadSensors() {
        NavUtils.openActivity(getActivity(), SensorActivity.class);
    }

    @OnClick(R.id.main_button_detectedactivity)
    void loadDetectedActivity() {
        NavUtils.openActivity(getActivity(), DetectedActivityActivity.class);
    }

    @OnClick(R.id.main_button_telephony)
    void loadTelephonyActivity() {
        NavUtils.openActivity(getActivity(), TelephonyActivity.class);
    }


    @OnClick(R.id.main_button_throw_exception)
    void loadThrowException() {
        NavUtils.openActivity(getActivity(), ThrowExceptionActivity.class);
    }

    @OnClick(R.id.main_button_job_scheduler)
    void loadJobScheduler() {
        NavUtils.openActivity(getActivity(), JobSchedulerActivity.class);
    }

    @OnClick(R.id.main_button_camera)
    void loadCamera() {
        Timber.d("Camera clicked");
        NavUtils.openActivity(getActivity(), CameraActivity.class);
    }

    @OnClick(R.id.main_button_realm)
    void loadRealm() {
        NavUtils.openActivity(getActivity(), RealmActivity.class);
    }

    @OnClick(R.id.main_button_coordinator)
    void loadCoordinator() {
        NavUtils.openActivity(getActivity(), CoordinatorLayoutActivity.class);
    }

    @OnClick(R.id.main_button_ble)
    void loadBleScanner(View view) {
        NavUtils.openActivity(getActivity(), BluetoothScannerActivity.class);
    }

    @OnClick(R.id.main_button_mvp)
    void loadMVP(View view) {
        NavUtils.openActivity(getActivity(), MvpActivity.class);
    }

    @OnClick(R.id.main_button_about)
    void loadAbout(View view) {
        NavUtils.openActivity(getActivity(), AboutActivity.class);
    }

    /*
    Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
    It can be used to do final initialization once these pieces are in place, such as retrieving views
    or restoring state. It is also useful for fragments that use setRetainInstance(boolean) to retain
    their instance, as this callback tells the fragment when it is fully associated with the new
    activity instance. This is called after onCreateView(LayoutInflater, ViewGroup, Bundle) and
    before onViewStateRestored(Bundle).
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifecycle("onActivityCreated");
        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    /*
    Called to ask the fragment to save its current dynamic state, so it can later be reconstructed in a
    new instance of its process is restarted. If a new instance of the fragment later needs to be created,
    the data you place in the Bundle here will be available in the Bundle given to onCreate(Bundle),
    onCreateView(LayoutInflater, ViewGroup, Bundle), and onActivityCreated(Bundle).

    This corresponds to Activity.onSaveInstanceState(Bundle) and most of the discussion there applies here
    as well. Note however: this method may be called at any time before onDestroy(). There are many
    situations where a fragment may be mostly torn down (such as when placed on the back stack with
    no UI showing), but its state will not be saved until its owning activity actually needs to save its state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifecycle("onSaveInstanceState");

        //Save the fragment's state here
    }

    /*
    Called when the Fragment is visible to the user. This is generally tied to Activity.onStart of
    the containing Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        lifecycle("onStart");
    }

    /*
    Called when the Fragment is no longer started. This is generally tied to Activity.onStop of
    the containing Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();
        lifecycle("onStop");
    }

    /*
    Called when the fragment is visible to the user and actively running.
    This is generally tied to Activity.onResume of the containing Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        lifecycle("onResume");
        // on resume gets called in sync with the activity,
        // add state in onCreate,
        // retrieve state in onActivityCreated & then execute code here
    }

    /*
    Initialize the contents of the Activity's standard options menu. You should place your menu items
    in to menu. For this method to be called, you must have first called setHasOptionsMenu(boolean).
    See Activity.onCreateOptionsMenu for more information.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        lifecycle("onOptionsItemSelected");
        inflater.inflate(R.menu.menu_fragment_main, menu);
    }

    /*
    Prepare the Screen's standard options menu to be displayed. This is called right before the
    menu is shown, every time it is shown. You can use this method to efficiently enable/disable
    items or otherwise dynamically modify the contents. See Activity.onPrepareOptionsMenu for more information.
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        lifecycle("onPrepareOptionsMenu");
    }

    /*
    This hook is called whenever an item in your options menu is selected. The default implementation
    simply returns false to have the normal processing happen (calling the item's Runnable or sending
    a message to its Handler as appropriate). You can use this method for any items for which you
    would like to do processing without those other facilities.

    Derived classes should call through to the base class for it to perform the default menu handling.
     */
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

    /*
    Called when the Fragment is no longer resumed. This is generally tied to Activity.onPause of the containing Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        lifecycle("onPause");
    }

    /*
    Called when a fragment is first attached to its activity. onCreate(Bundle) will be called after this.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        lifecycle("onAttach");
        if (activity instanceof MainActivity) {
            listener = (OnEventListener) activity;
        }
    }

    /*
    Called when the view previously created by onCreateView(LayoutInflater, ViewGroup, Bundle) has
    been detached from the fragment. The next time the fragment needs to be displayed, a new view
    will be created. This is called after onStop() and before onDestroy(). It is called regardless
    of whether onCreateView(LayoutInflater, ViewGroup, Bundle) returned a non-null view.
    Internally it is called after the view's state has been saved but before it has been removed from its parent.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifecycle("onDestroyView");
        unbinder.unbind();
    }

    /*
    Called when the fragment is no longer in use. This is called after onStop() and before onDetach().
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle("onDestroy");
    }

    /*
    Set a hint to the system about whether this fragment's UI is currently visible to the user.
    This hint defaults to true and is persistent across fragment instance state save and restore.

    An app may set this to false to indicate that the fragment's UI is scrolled out of visibility
    or is otherwise not directly visible to the user. This may be used by the system to prioritize
    operations such as fragment lifecycle updates or loader ordering behavior.

    *  use instead of onStart/onStop when the fragment is child of a viewpager
    */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lifecycle("setUserVisibleHint:isVisibleToUser -> " + isVisibleToUser);
    }

    /*
    Called when the hidden state (as returned by isHidden() of the fragment has changed.
    Fragments start out not hidden; this will be called whenever the fragment changes state from that.
     */
    @Override
    public void onHiddenChanged(boolean isHidden) {
        super.onHiddenChanged(isHidden);
        lifecycle("onHiddenChanged:isHidden -> " + isHidden);
    }

    /*
    Called when all saved state has been restored into the view hierarchy of the fragment.
    This can be used to do initialization based on saved state that you are letting the view
    hierarchy track itself, such as whether check box widgets are currently checked.
    This is called after onActivityCreated(Bundle) and before onStart().
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        lifecycle("onViewStateRestored");
    }

    /*
    Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any
    saved state has been restored in to the view. This gives subclasses a chance to initialize themselves
    once they know their view hierarchy has been completely created. The fragment's view hierarchy is not
    however attached to its parent at this point.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycle("onViewStateRestored");
    }

    /*
    Called when a fragment is being created as part of a view layout inflation, typically from
    setting the content view of an activity. This may be called immediately after the fragment
    is created from a tag in a layout file. Note this is before the fragment's onAttach(Activity)
    has been called; all you should do here is parse the attributes and save them away.

    This is called every time the fragment is inflated, even if it is being inflated into a new
    instance with saved state. It typically makes sense to re-parse the parameters each time, to
    allow them to change with different configurations.
     */
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        lifecycle("onInflate");
    }

    /*
    Called when the fragment is no longer attached to its activity. This is called after onDestroy().
     */
    @Override
    public void onDetach() {
        super.onDetach();
        lifecycle("onDetach");
    }

    @Override
    public String toString() {
        // getClass().getName() + '@' + Integer.toHexString(hashCode())
        return super.toString();
    }

    // communicate with the activity via a listener
    private void doSomeThing(String data) {
        listener.onEvent(data);
    }

    private void lifecycle(String methodName) {
        Timber.d("Fragment -> %s", methodName);
    }

    public interface OnEventListener {
        void onEvent(String data);
    }

}
