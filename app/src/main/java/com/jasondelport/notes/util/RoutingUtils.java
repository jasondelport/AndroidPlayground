package com.jasondelport.notes.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jasondelport on 04/05/15.
 */
public class RoutingUtils {

    public static void redirectTo(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    public static void openActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public static void addFragmentToStack(Activity activity, Fragment newFragment, int viewId, String tag) {
        FragmentManager fm = activity.getFragmentManager();
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment == null) {
            fm.beginTransaction()
                    .add(viewId, newFragment, tag)
                    .addToBackStack(tag)
                    .commit();
        } else {
            if (existingFragment.isDetached()) {
                fm.beginTransaction().attach(existingFragment).commit();
            }
            if (existingFragment.isHidden()) {
                fm.beginTransaction().show(existingFragment).commit();
            }
        }
    }

    public static void addFragment(Activity activity, Fragment newFragment, int viewId, String tag) {
        FragmentManager fm = activity.getFragmentManager();
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment == null) {
            fm.beginTransaction()
                    .add(viewId, newFragment, tag)
                    .commit();
        } else {
            if (existingFragment.isDetached()) {
                fm.beginTransaction().attach(existingFragment).commit();
            }
            if (existingFragment.isHidden()) {
                fm.beginTransaction().show(existingFragment).commit();
            }
        }
    }
}