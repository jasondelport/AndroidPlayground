package com.jasondelport.notes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasondelport.notes.R;
import com.jasondelport.notes.data.singleton.ExampleSingleton;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class RXJavaFragment extends BaseFragment {

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

        List<String> list = Arrays.asList("one", "two", "three", "four", "five");

        //Observable.just("one", "two", "three", "four", "five")
        Observable.from(list).delay(5, TimeUnit.SECONDS).skip(2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        addToTextView(s);
                    }

                    @Override
                    public void onCompleted() {
                        Timber.d("RXJava Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e, "RXJava Error");
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxjava, container, false);
        ButterKnife.bind(this, view);
        if (ExampleSingleton.isNull()) {
            Timber.d("singleton is null");
            getActivity().finish();
        } else {
            Timber.d("singleton is not null -> %s", ExampleSingleton.getInstance().getHelloWorld());
        }
        return view;
    }

    private void addToTextView(String newText) {
        String existingText = text.getText().toString();
        existingText += newText + "\n";
        text.setText(existingText);
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
