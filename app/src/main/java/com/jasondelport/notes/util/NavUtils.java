package com.jasondelport.notes.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

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

    public static void replaceTopFragment(FragmentManager fm, Fragment fragment, int viewId, String tag) {
        fm.popBackStack();
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment == null) {
            fm.beginTransaction()
                    .add(viewId, fragment, tag)
                    .addToBackStack(tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
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

     /*
    Replace an existing fragment that was added to a container.
    This is essentially the same as calling remove(Fragment)
    for all currently added fragments that were added with
    the same containerViewId and then add(int, Fragment, String)
    with the same arguments given here.
     */

    public static void replaceFragment(FragmentManager fm, Fragment fragment, int viewId, String tag) {
        fm.popBackStack();
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment == null) {
            fm.beginTransaction()
                    .replace(viewId, fragment, tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
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

    public static void replaceAllFragments(FragmentManager fm, Fragment fragment, int viewId, String tag) {
        clearBackStack(fm);
        addFragment(fm, fragment, viewId, tag);
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

    public static void clearBackStack(FragmentManager fm) {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
