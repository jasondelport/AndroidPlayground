package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasondelport.notes.R;
import com.jasondelport.notes.listener.OnNavigationEventListener;
import com.jasondelport.notes.ui.activity.DrawerActivity;
import com.jasondelport.notes.ui.adapter.ViewPagerAdapter;

import butterknife.ButterKnife;


public class DrawerContentParentFragment extends BaseFragment implements OnNavigationEventListener {

    private String name;
    private OnNavigationEventListener onNavigationEventListener;

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
        this.setHasOptionsMenu(false);
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
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        // new
        //tabLayout.getSelectedTabPosition();

        return view;
    }

    /*
    private String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }
    */

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(DrawerContentChildFragment.newInstance(name + " child 1"), "child 1");
        adapter.addFragment(DrawerContentChildFragment.newInstance(name + " child 2"), "child 2");
        adapter.addFragment(DrawerContentChildFragment.newInstance(name + " child 3"), "child 3");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).setOnNavigationEventListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((DrawerActivity) getActivity()).setOnNavigationEventListener(null);
    }

    @Override
    public void onHomePressed() {
        if (onNavigationEventListener != null) {
            onNavigationEventListener.onHomePressed();
        } else {
            ((DrawerActivity) getActivity()).getDrawerLayout().openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (onNavigationEventListener != null) {
            onNavigationEventListener.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
            ((DrawerActivity) getActivity()).getNavigationView().getMenu().getItem(0).setChecked(true);
        }
    }

    public void setOnNavigationEventListener(OnNavigationEventListener onNavigationEventListener) {
        this.onNavigationEventListener = onNavigationEventListener;
    }
}
