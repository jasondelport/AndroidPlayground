package com.jasondelport.playground.service;


import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;

import com.jasondelport.playground.ui.fragment.JobSchedulerFragment;

import java.util.LinkedList;

import timber.log.Timber;

public class TestJobService extends JobService {

    private final LinkedList<JobParameters> jobParamsMap = new LinkedList<>();
    private JobSchedulerFragment mFragment;
    private UndertakeTask mUndertakeTask = null;

    @Override
    public void onCreate() {
        Timber.d("onCreate");
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy");
        super.onDestroy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("onStartCommand");
        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = JobSchedulerFragment.MSG_SERVICE_OBJ;
        m.obj = this;
        try {
            callback.send(m);
        } catch (Exception e) {
            Timber.e(e, "Error -> %s", e);
        }
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Timber.d("Start job. ID -> %d", params.getJobId());
        jobParamsMap.add(params);
        if (mFragment != null) {
            mFragment.onReceivedStartJob(params);
        }
        mUndertakeTask = new UndertakeTask();
        mUndertakeTask.execute();
        return false;
    }

    // This method is called if the system has determined that you must stop
    // execution of your job even before you've had a chance to call
    // jobFinished(JobParameters, boolean).
    @Override
    public boolean onStopJob(JobParameters params) {
        Timber.d("Stop job. ID -> %d", params.getJobId());
        jobParamsMap.remove(params);
        if (mFragment != null) {
            mFragment.onReceivedStopJob();
        }
        return false;
    }

    public void setUiCallback(JobSchedulerFragment fragment) {
        mFragment = fragment;
    }

    public void scheduleJob(JobInfo job) {
        Timber.d("Schedule job");
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(job);
    }

    public boolean callJobFinished() {
        Timber.d("callJobFinished");
        mUndertakeTask.cancel(true);
        JobParameters params = jobParamsMap.poll();
        if (params == null) {
            return false;
        } else {
            jobFinished(params, false);
            if (mFragment != null) {
                mFragment.onReceivedFinishJob();
            }
            return true;
        }
    }

    private class UndertakeTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Timber.d("Starting task.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            Timber.d("Cancel task.");
        }

        @Override
        protected void onPostExecute(Void v) {
            Timber.d("Finished task.");
            callJobFinished();
            super.onPostExecute(v);
        }
    }
}
