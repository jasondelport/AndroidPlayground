package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class RXJavaFragment extends BaseFragment {

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            text.setText(s);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }
    };

    @Bind(R.id.text)
    TextView text;

    public RXJavaFragment() {

    }

    public static RXJavaFragment newInstance() {
        RXJavaFragment fragment = new RXJavaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");
        setRetainInstance(false);
        setHasOptionsMenu(false);

        // this will run every time as long as setRetainInstance = false
        Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxjava, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifecycle("onDestroyView");
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle("onDestroy");
    }

    private void lifecycle(String methodName) {
        Timber.d("Fragment (%s)", methodName);
    }

}
