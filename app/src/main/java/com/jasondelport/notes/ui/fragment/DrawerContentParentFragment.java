package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jasondelport.notes.R;
import com.jasondelport.notes.adapter.ViewPagerAdapter;
import com.jasondelport.notes.listener.OnBackPressedListener;
import com.jasondelport.notes.ui.activity.DrawerActivity;

import butterknife.ButterKnife;


public class DrawerContentParentFragment extends BaseFragment implements OnBackPressedListener {

    private String name;
    private OnBackPressedListener onBackPressedListener;

    public DrawerContentParentFragment() {

    }

    public static DrawerContentParentFragment newInstance(String name) {
        DrawerContentParentFragment fragment = new DrawerContentParentFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(false);
        this.setHasOptionsMenu(true);
        name = getArguments().getString("name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_content_parent, container, false);
        ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        if (viewPager != null) {

            setupViewPager(viewPager);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            // have to set this listener before setUpWithViewPager is called
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    DrawerContentChildFragment childFragment = (DrawerContentChildFragment)
                            getChildFragmentManager().findFragmentByTag(getFragmentTag(viewPager.getId(), tab.getPosition()));

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            tabLayout.setupWithViewPager(viewPager);

        }

        return view;
    }

    private String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(DrawerContentChildFragment.newInstance(name + " child 1"), "child 1");
        adapter.addFragment(DrawerContentChildFragment.newInstance(name + " child 2"), "child 2");
        adapter.addFragment(DrawerContentChildFragment.newInstance(name + " child 3"), "child 3");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // return false to let the child of this fragment deal with it
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Timber.d("onResume");
        ((DrawerActivity) getActivity()).setOnBackPressedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Timber.d("onPause");
        ((DrawerActivity) getActivity()).setOnBackPressedListener(null);
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}
