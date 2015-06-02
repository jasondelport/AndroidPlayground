package com.jasondelport.notes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public static final String ALARM_ACTION = "co.uk.keytree.alarm.awake";

    @InjectView(R.id.button_keep_awake)
    Button button1;

    @InjectView(R.id.button_sleep)
    Button button2;

    @InjectView(R.id.button_alarm)
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");
        setContentView(R.layout.activity_keepawake);
        ButterKnife.inject(this);

        handleIntent(getIntent());

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
                setAlarm();
            }
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
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
        runOnUiThread(
                new Runnable() {
                    public void run() {
                        KeepAwakeActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                }
        );
    }

    public void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 23);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.setAction(ALARM_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 20, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    private void handleIntent(Intent intent) {
        lifecycle("handleIntent");
        boolean keepAwake = intent.getBooleanExtra("keepAwake", false);
        if (keepAwake) {
            keepAwake();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycle("onResume");
        handleIntent(getIntent());

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lifecycle("onNewIntent");
        handleIntent(intent);
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