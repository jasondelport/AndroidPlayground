package com.jasondelport.playground;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;

import com.jasondelport.playground.ui.activity.MainActivity;
import com.jasondelport.playground.ui.fragment.MainFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {
    private MainActivity activity;
    private FragmentManager manager;
    private Fragment fragment;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
        manager = activity.getFragmentManager();
        fragment = MainFragment.newInstance("hello", 0);
    }

    @Test
    public void shouldFindFragmentByTag() throws Exception {
        manager.beginTransaction()
                .add(android.R.id.content, fragment, "MainFragment")
                .commit();

        assertThat(manager.findFragmentByTag("MainFragment"), sameInstance(fragment));
    }
}