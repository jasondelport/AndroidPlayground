package com.jasondelport.playground.ui.fragment;

import android.app.Fragment;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.playground.R;
import com.jasondelport.playground.service.TestJobService;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;


public class JobSchedulerFragment extends BaseFragment {

    public static final int MSG_SERVICE_OBJ = 2;
    private static int mJobId = 0;
    private String output = "";
    @BindView(R.id.output1)
    TextView output1;
    private TestJobService mTestService;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SERVICE_OBJ:
                    Timber.d("Service started");
                    output = "Service started";
                    setText();
                    mTestService = (TestJobService) msg.obj;
                    mTestService.setUiCallback(JobSchedulerFragment.this);
            }
        }
    };
    private ComponentName mServiceComponent;
    private Unbinder unbinder;

    public static Fragment newInstance() {
        return new JobSchedulerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);
        this.setMenuVisibility(false);
        this.setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_scheduler, container, false);
        unbinder = ButterKnife.bind(this, view);

        Timber.d("About to start service");
        Intent startServiceIntent = new Intent(getActivity(), TestJobService.class);
        startServiceIntent.putExtra("messenger", new Messenger(mHandler));
        getActivity().startService(startServiceIntent);

        return view;
    }


    @OnClick(R.id.job_button1)
    void loadStartJob(View view) {
        scheduleJob();
    }

    @OnClick(R.id.job_button2)
    void loadFinishJob(View view) {
        finishJob();
    }

    @OnClick(R.id.job_button3)
    void loadCancelAll(View view) {
        cancelAllJobs();
    }

    public void scheduleJob() {
        Timber.d("scheduleJob");
        mServiceComponent = new ComponentName(getActivity(), TestJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(mJobId++, mServiceComponent);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPeriodic(60000); // every 60 seconds
        mTestService.scheduleJob(builder.build());
    }

    public void finishJob() {
        Timber.d("finishJob");
        boolean finished = mTestService.callJobFinished();
        Timber.d("Was job finished? -> %b", finished);
    }

    public void cancelAllJobs() {
        Timber.d("cancelAllJobs");
        JobScheduler tm = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
        output = "Job Cancelled\n";
    }

    public void onReceivedStartJob(JobParameters params) {
        Timber.d("onReceivedStartJob. Params -> %s", params.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String time = sdf.format(new Date());
        output = "Starting Job -> "+time+"\n" + output;
        setText();
    }

    public void onReceivedStopJob() {
        Timber.d("onReceivedStopJob");
    }

    public void onReceivedFinishJob() {
        Timber.d("onReceivedFinishJob");
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String time = sdf.format(new Date());
        output = "Finishing Job -> "+time+"\n" + output;
        setText();
    }

    private void setText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output1.setText(output);
            }
        });
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
