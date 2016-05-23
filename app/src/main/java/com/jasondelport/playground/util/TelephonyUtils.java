package com.jasondelport.playground.util;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import timber.log.Timber;

/**
 * Created by jasondelport on 23/05/16.
 */
public class TelephonyUtils {

    public static void endCall(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");
            methodGetITelephony.setAccessible(true);

            /*
            // version 1
            com.android.internal.telephony.ITelephony telephonyService = (ITelephony) methodGetITelephony.invoke(telephonyManager);
            telephonyService = (ITelephony) methodGetITelephony.invoke(telephonyManager);
            telephonyService.silenceRinger();
            */

            // version 2
            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);


            Timber.d("End call occurred.");
        } catch (Exception e) {
            Timber.e(e, "Error -> %s", e.getMessage());
        }
    }

    public static boolean silentRinger(Context context) {
        try {
            AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            return true;
        } catch (Exception e) {
            Timber.e(e, "Error -> %s", e.getMessage());
            return false;
        }
    }

    public static boolean normalRinger(Context context) {
        try {
            AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            return true;
        } catch (Exception e) {
            Timber.e(e, "Error -> %s", e.getMessage());
            return false;
        }
    }

    public static String ringerStatus(Context context) {
        try {
            AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (manager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                return "RINGER_MODE_NORMAL";
            } else if (manager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                return "RINGER_MODE_SILENT";
            } else {
                return "RINGER_MODE_VIBRATE";
            }
        } catch (Exception e) {
            Timber.e(e, "Error -> %s", e.getMessage());
            return "ERROR";
        }
    }

    public static void silenceCall(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");
            methodGetITelephony.setAccessible(true);

            /*
            // version 1
            com.android.internal.telephony.ITelephony telephonyService = (ITelephony) methodGetITelephony.invoke(telephonyManager);
            telephonyService = (ITelephony) methodGetITelephony.invoke(telephonyManager);
            telephonyService.silenceRinger();
            */

            // version 2
            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
            Method methodSilenceRinger = telephonyInterfaceClass.getDeclaredMethod("silenceRinger");

            // Invoke silenceCall()
            methodSilenceRinger.invoke(telephonyInterface);

            Timber.d("Silenced call occurred.");
        } catch (Exception e) {
            Timber.e(e, "Error -> %s", e.getMessage());
        }
    }

}
