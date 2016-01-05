package com.jasondelport.playground.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jasondelport.playground.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by jasondelport on 06/02/2015.
 */
public class Utils {

    public static int dpToPx(int dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return Math.round(px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static Point getScreenDimensionsInDp(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return new Point(configuration.screenWidthDp, configuration.screenHeightDp);
    }

    // this had issues, use cautiously
    public static long getDateDiff(Date startDate, Date endDate, TimeUnit timeUnit) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    public static long daysBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        Calendar date = (Calendar) start.clone();
        long daysBetween = 0;
        while (date.before(end)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    public static boolean isEmptyString(String string) {
        return string == null || TextUtils.isEmpty(string) || string.equalsIgnoreCase("null");
    }

    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty() || list.size() == 0;
    }

    public static int getNumber(String string) {
        int number = 0;
        if (!isEmptyString(string) && TextUtils.isDigitsOnly(string)) {
            number = Integer.parseInt(string);
        }
        return number;
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);

    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void savePreference(Context context, String key, Object value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE).edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            Timber.e("Preference did not match one of the main primitive object types.");
        }
        editor.apply();
    }

    public static boolean preferenceExists(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return prefs.contains(key);
    }

    public static void deletePreference(Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clearPreferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit(); // keep this synchronous
    }

    public static Map<String, ?> getAllPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getAll();
    }

    public static boolean getBoolPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static int getIntPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(key, null);
    }

    public static long getLongPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getLong(key, 0L);
    }

    public static boolean isOnline(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnectedOrConnecting());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isInLandscapeOrientation(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean hasSmallScreen(Context context) {
        return getScreenSize(context) == Configuration.SCREENLAYOUT_SIZE_SMALL;
    }

    public static boolean hasNormalScreen(Context context) {
        return getScreenSize(context) == Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }

    public static boolean hasLargeScreen(Context context) {
        return getScreenSize(context) == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean hasXLargeScreen(Context context) {
        return getScreenSize(context) == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public static int getScreenSize(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }


}
