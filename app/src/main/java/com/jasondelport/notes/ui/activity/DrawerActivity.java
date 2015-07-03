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
import com.jasondelport.notes.ui.fragment.DrawerContentParentFragment;
import com.jasondelport.notes.util.NavUtils;

public class DrawerActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Fragment fragment;
    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (savedInstanceState == null) {
            fragment = DrawerContentParentFragment.newInstance("parent 1");
        } else {
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        }

        NavUtils.addFragment(getFragmentManager(), fragment, R.id.main_drawer_content, Constants.FRAGMENT_CONTENT_1);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            if (onBackPressedListener != null) {
                onBackPressedListener.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
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
            case Constants.FRAGMENT_CONTENT_1:
                NavUtils.clearBackStack(getFragmentManager());
                break;
            case Constants.FRAGMENT_CONTENT_2:
                Fragment navFragment = DrawerContentParentFragment.newInstance("parent 2");
                NavUtils.addFragmentToStack(getFragmentManager(), navFragment, R.id.main_drawer_content, tag);
                MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_2);
                menuItem.setChecked(true);
                break;
            case Constants.FRAGMENT_CONTENT_3:
                Fragment newFragment = DrawerContentParentFragment.newInstance("parent 3");
                NavUtils.addFragmentToStack(getFragmentManager(), newFragment, R.id.main_drawer_content, tag);
                MenuItem item = navigationView.getMenu().findItem(R.id.nav_3);
                item.setChecked(true);
                break;
            case Constants.FRAGMENT_CONTENT_4:
                Fragment aFragment = DrawerContentParentFragment.newInstance("parent 4");
                NavUtils.addFragmentToStack(getFragmentManager(), aFragment, R.id.main_drawer_content, tag);
                MenuItem itema = navigationView.getMenu().findItem(R.id.nav_4);
                itema.setChecked(true);
                break;
            case Constants.ACTIVITY_RXJAVA:
                NavUtils.openActivityAndFinish(this, RXJavaActivity.class);
                break;
        }
    }

}