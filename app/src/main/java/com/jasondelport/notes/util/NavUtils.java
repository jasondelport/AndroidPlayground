package com.jasondelport.notes.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

/**
 * Created by jasondelport on 04/05/15.
 */
public class NavUtils {

    public static void openActivityAndFinish(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void openActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    public static void openActivityWithAnimation(Activity activity, Class clazz, int enterAnim, int exitAnim) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        //activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    public static void addFragmentToStack(FragmentManager fm, Fragment fragment, int viewId, String tag) {
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment == null) {
            fm.beginTransaction()
                    .add(viewId, fragment, tag)
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

    public static void addFragment(FragmentManager fm, Fragment fragment, int viewId, String tag) {
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment == null) {
            fm.beginTransaction()
                    .add(viewId, fragment, tag)
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
