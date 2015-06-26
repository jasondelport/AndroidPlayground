package com.jasondelport.notes.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

import timber.log.Timber;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleAlarms(context);
    }

    private void scheduleAlarms(Context context) {
        Timber.d("rescheduling alarms");
        SharedPreferences prefs = context.getSharedPreferences("uk.co.keytree.matrix", Context.MODE_PRIVATE);
        if (prefs.contains("wakeAlarmHour") && prefs.contains("wakeAlarmMinute")) {
            setWakeAlarm(context, prefs.getInt("wakeAlarmHour", 6), prefs.getInt("wakeAlarmMinute", 0));
        }
        if (prefs.contains("sleepAlarmHour") && prefs.contains("sleepAlarmMinute")) {
            setSleepAlarm(context, prefs.getInt("sleepAlarmHour", 20), prefs.getInt("sleepAlarmMinute", 0));
        }
    }

    private void setWakeAlarm(Context context, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent wakeIntent = new Intent(context, AlarmReceiver.class);
        wakeIntent.setAction("uk.co.keytree.alarm.wake");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, wakeIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    private void setSleepAlarm(Context context, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent sleepIntent = new Intent();
        sleepIntent.setAction("uk.co.keytree.alarm.sleep");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, sleepIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
