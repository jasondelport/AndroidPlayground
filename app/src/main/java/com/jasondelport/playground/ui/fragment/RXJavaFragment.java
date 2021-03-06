package com.jasondelport.playground.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jasondelport.playground.R;
import com.jasondelport.playground.data.singleton.ExampleSingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class RXJavaFragment extends BaseFragment {

    @BindView(R.id.text)
    TextView text;
    private Unbinder unbinder;

    public RXJavaFragment() {

    }

    //@DebugLog
    public static RXJavaFragment newInstance() {
        RXJavaFragment fragment = new RXJavaFragment();
        return fragment;
    }

    //@DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle("onCreate");
        setRetainInstance(false);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lifecycle("onCreateView");
        View view = inflater.inflate(R.layout.fragment_rxjava, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (ExampleSingleton.isNull()) {
            Timber.d("singleton is null");
        } else {
            Timber.d("singleton is not null -> %s", ExampleSingleton.getInstance().getHelloWorld());
        }

        RxTextView.textChangeEvents(text)
                .subscribe(e -> Timber.d(e.text().toString()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        List<String> list = Arrays.asList("one", "two", "three", "four", "five");

        //Observable.just("one", "two", "three", "four", "five")
        Observable.from(list).delay(5, TimeUnit.SECONDS).skip(2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())  // AndroidSchedulers.io & AndroidSchedulers..computation
                .filter(items -> items.length() > 2)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        addToTextView(s);
                    }

                    @Override
                    public void onCompleted() {
                        Timber.d("RXJava Subscriber Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "RXJava Subscriber Error");
                    }
                });

        Observable
                .just(1, 2, 3, 4, 5)
                        //.map(list -> throwException(list))
                .last()
                .subscribe(System.out::println);


        /*
        new RestService().getAllItems()
                .doOnSubscribe(() -> {
                    // start request
                    // show spinner
                })
                .doOnCompleted(() -> {
                    // finished request
                    // hide spinner
                })
                // request details for each item with id
                .flatMap(itemId -> new RestService().getItemDetailById(itemId))
                .doOnError(throwable -> {
                    // log the error
                })
                .onErrorResumeNext(Observable.< ~ > empty())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    // do something with list
                });
        */
    }

    /*
    command + shift + a
    extend selection [alt + up/down]

    Intention Actions
    alt + enter

    List<String> list = new ArrayList<>();
    list.fori [tab]

    //logging shortcuts
    logt
    logm
    logr

     */

    private void addToTextView(String newText) {
        List<String> list = new ArrayList<>();
        if (text != null) {
            String existingText = text.getText().toString();
            existingText += newText + "\n";
            text.setText(existingText);
        }
    }

    @Override
    public void onDestroyView() {
        lifecycle("onDestroyView");
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        ExampleSingleton.destroy();
        lifecycle("onDestroy");
        super.onDestroy();
    }

    private void lifecycle(String methodName) {
        Timber.d("Fragment (%s)", methodName);
    }

}
