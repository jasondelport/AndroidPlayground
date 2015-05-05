package com.jasondelport.notes.util;

import android.app.Activity;
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

    private static void openActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}