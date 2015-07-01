package com.jasondelport.notes.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.jasondelport.notes.Constants;
import com.jasondelport.notes.R;
import com.jasondelport.notes.listener.OnBackPressedListener;
import com.jasondelport.notes.ui.fragment.DrawerContentFragment;
import com.jasondelport.notes.util.NavUtils;

public class DrawerActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private Fragment fragment;
    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (savedInstanceState == null) {
            fragment = DrawerContentFragment.newInstance("page 1");
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        }

        NavUtils.addFragment(getFragmentManager(), fragment, R.id.content, Constants.FRAGMENT_CONTENT_1);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            onBackPressedListener.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                // set to false so the event propagates to the fragment
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                loadContent(Constants.FRAGMENT_CONTENT_2);
                                break;
                            case R.id.nav_messages:
                                loadContent(Constants.FRAGMENT_CONTENT_3);
                                break;
                            case R.id.nav_friends:
                                loadContent(Constants.ACTIVITY_PERCENT);
                                break;
                            case R.id.nav_discussion:
                                loadContent(Constants.ACTIVITY_RXJAVA);
                                break;
                        }

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void loadContent(String tag) {
        switch (tag) {
            case Constants.FRAGMENT_CONTENT_2:
                Fragment navFragment = DrawerContentFragment.newInstance("page 2");
                NavUtils.addFragmentToStack(getFragmentManager(), navFragment, R.id.content, tag);
                break;
            case Constants.FRAGMENT_CONTENT_3:
                Fragment newFragment = DrawerContentFragment.newInstance("page 3");
                NavUtils.addFragmentToStack(getFragmentManager(), newFragment, R.id.content, tag);
                break;
            case Constants.ACTIVITY_PERCENT:
                NavUtils.openActivity(this, PercentActivity.class);
                break;
            case Constants.ACTIVITY_RXJAVA:
                NavUtils.openActivityAndFinish(this, RXJavaActivity.class);
                break;
        }
    }

}