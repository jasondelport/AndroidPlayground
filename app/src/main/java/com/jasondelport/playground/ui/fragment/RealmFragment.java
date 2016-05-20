package com.jasondelport.playground.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.jasondelport.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;


public class RealmFragment extends BaseFragment  {

    @BindView(R.id.output1)
    TextView output1;

    private Unbinder unbinder;
    private Subscription subscription;

    Subscriber<ActivityRecognitionResult> subscriber = new Subscriber<ActivityRecognitionResult>() {
        @Override
        public void onNext(ActivityRecognitionResult detectedActivity) {
            Timber.d("RXJava OnNext");
            output1.setText(detectedActivity.getMostProbableActivity().toString());
        }

        @Override
        public void onCompleted() {
            Timber.d("RXJava onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Timber.e("RXJava Error -> %s", e.getMessage());
        }
    };

    public static Fragment newInstance() {
        Fragment fragment = new RealmFragment();
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_realm, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        subscription.unsubscribe();
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
