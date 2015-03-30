package com.jasondelport.notes.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontUtils {
    private static Typeface robotoTypeFace;
    /*
    if (Build.VERSION.SDK_INT < 11) {
        ViewGroup godfatherView = (ViewGroup)this.getWindow().getDecorView();
        FontUtils.setRobotoFont(this, godfatherView);
    }
    */
    public static void setRobotoFont(Context context, View view) {
        if (robotoTypeFace == null) {
            robotoTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto/Roboto-Regular.ttf");
        }
        setFont(view, robotoTypeFace);
    }

    private static void setFont(View view, Typeface robotoTypeFace) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setFont(((ViewGroup) view).getChildAt(i), robotoTypeFace);
            }
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(robotoTypeFace);
        }
    }
}