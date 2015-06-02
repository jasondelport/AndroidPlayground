package com.jasondelport.notes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import timber.log.Timber;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("onReceive action -> %s", intent.getAction());
        if (intent.getAction().equals("uk.co.keytree.alarm.wake")) {
            wakeUpDevice(context);
        }
    }

    private void wakeUpDevice(Context context) {
        Timber.d("waking up");
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
        wakeLock.acquire();
        wakeLock.release();
    }
}