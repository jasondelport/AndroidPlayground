package com.jasondelport.notes.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.os.PowerManager;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
/**
 * Created by jasondelport on 13/11/15.
 */
public class DebugUtils {

    public static void riseAndShine(Activity activity) {

        KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(KEYGUARD_SERVICE);
        final KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("Unlock!");
        keyguardLock.disableKeyguard();

        activity.getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);

        PowerManager powerManager = (PowerManager) activity.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, "Wakeup!");
        wakeLock.acquire();
        wakeLock.release();

    }

}