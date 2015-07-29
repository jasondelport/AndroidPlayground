package com.jasondelport.notes.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.jasondelport.notes.Constants;
import com.jasondelport.notes.R;
import com.jasondelport.notes.listener.OnNavigationEventListener;
import com.jasondelport.notes.ui.fragment.DrawerContentParentFragment;
import com.jasondelport.notes.ui.fragment.DrawerHomeFragment;
import com.jasondelport.notes.util.NavUtils;

public class DrawerActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Fragment fragment;
    private OnNavigationEventListener onNavigationEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        if (savedInstanceState == null) {
            fragment = DrawerHomeFragment.newInstance("parent 1");
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        }

        NavUtils.addFragment(getFragmentManager(), fragment, R.id.main_drawer_content, Constants.FRAGMENT_CONTENT_HOME);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            // defer popping the backstack to the listener
            if (onNavigationEventListener != null) {
                onNavigationEventListener.onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // defer responsibility to the listener if it exists
                if (onNavigationEventListener != null) {
                    onNavigationEventListener.onHomePressed();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                // return false only if you want the event to propagate downward
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        closeDrawerHandler.sendEmptyMessage(0);

                        switch (menuItem.getItemId()) {
                            case R.id.nav_1:
                                loadContent(Constants.FRAGMENT_CONTENT_1);
                                break;
                            case R.id.nav_2:
                                loadContent(Constants.FRAGMENT_CONTENT_2);
                                break;
                            case R.id.nav_3:
                                loadContent(Constants.FRAGMENT_CONTENT_3);
                                break;
                            case R.id.nav_4:
                                loadContent(Constants.FRAGMENT_CONTENT_4);
                                break;
                            case R.id.nav_5:

                                break;
                            case R.id.nav_6:

                                break;
                        }

                        return true;
                    }
                });
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    public void setOnNavigationEventListener(OnNavigationEventListener onNavigationEventListener) {
        this.onNavigationEventListener = onNavigationEventListener;
    }

    Handler closeDrawerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mDrawerLayout.closeDrawers();
            return true;
        }
    });

    public void loadContent(String tag) {
        switch (tag) {
            case Constants.FRAGMENT_CONTENT_1:
                NavUtils.clearBackStack(getFragmentManager());
                break;
            case Constants.FRAGMENT_CONTENT_2:
                Fragment navFragment = DrawerContentParentFragment.newInstance("parent 2");
                NavUtils.replaceTopFragment(getFragmentManager(), navFragment, R.id.main_drawer_content, tag);
                break;
            case Constants.FRAGMENT_CONTENT_3:
                Fragment newFragment = DrawerContentParentFragment.newInstance("parent 3");
                NavUtils.replaceTopFragment(getFragmentManager(), newFragment, R.id.main_drawer_content, tag);
                break;
            case Constants.FRAGMENT_CONTENT_4:
                Fragment aFragment = DrawerContentParentFragment.newInstance("parent 4");
                NavUtils.replaceTopFragment(getFragmentManager(), aFragment, R.id.main_drawer_content, tag);
                break;
            case Constants.ACTIVITY_RXJAVA:
                NavUtils.openActivityAndFinish(this, RXJavaActivity.class);
                break;
        }
    }

}