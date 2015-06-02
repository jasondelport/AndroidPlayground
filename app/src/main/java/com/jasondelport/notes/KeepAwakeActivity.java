package com.jasondelport.notes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

public class KeepAwakeActivity extends AppCompatActivity {

    @InjectView(R.id.button_keep_awake)
    Button button1;
    @InjectView(R.id.button_sleep)
    Button button2;
    @InjectView(R.id.button_alarm)
    Button button3;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");
        setContentView(R.layout.activity_keepawake);
        ButterKnife.inject(this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keepAwake();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowSleep();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                setWakeAlarm(hour, minute + 1);
                setSleepAlarm(hour, minute + 2);
                button3.setText("Alarms Set");
                Toast.makeText(KeepAwakeActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();
                allowSleep();
            }
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Timber.d("onReceive action -> %s", intent.getAction());
                allowSleep();
            }
        };

    }

    private void keepAwake() {
        Timber.d("keep awake");
        runOnUiThread(
                new Runnable() {
                    public void run() {
                        KeepAwakeActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                }
        );
    }

    private void allowSleep() {
        Timber.d("allow sleep");
        runOnUiThread(
                new Runnable() {
                    public void run() {
                        KeepAwakeActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                }
        );
    }

    public void setWakeAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent wakeIntent = new Intent(this, AlarmReceiver.class);
        wakeIntent.setAction("uk.co.keytree.alarm.wake");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, wakeIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    public void setSleepAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent sleepIntent = new Intent();
        sleepIntent.setAction("uk.co.keytree.alarm.sleep");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, sleepIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycle("onResume");
        keepAwake();
        if (receiver != null) {
            registerReceiver(receiver, new IntentFilter("uk.co.keytree.alarm.sleep"));
            Timber.d("registering receiver");
        }
    }

    @Override
    protected void onPause() {
        lifecycle("onPause");
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            Timber.d("unregistering receiver");
        }
    }

    @Override
    protected void onDestroy() {
        lifecycle("onDestroy");
        super.onDestroy();
    }

    private void lifecycle(String methodName) {
        Timber.d("%s (%s)", getClass().getSimpleName(), methodName);
    }
}