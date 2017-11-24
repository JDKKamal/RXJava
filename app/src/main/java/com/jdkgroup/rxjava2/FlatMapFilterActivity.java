package com.jdkgroup.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import com.jdkgroup.rxjava.R;
public class FlatMapFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flapMapFilterOddEven();
        flapMapFilterList();
    }

    private void flapMapFilterOddEven() {
        List<Integer> ints = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            ints.add(new Integer(i));
        }
        Log.d("flatMap", "1,2,3,4,5,6,7,8,9");

        Observable.just(ints)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(ints1 -> Observable.fromIterable(ints1))
                .filter(integer -> {
                    Log.d("flatMap", "filter out odd numbers.........");
                    return integer.intValue() % 2 == 0;
                })
                .flatMap(new Function<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> apply(Integer integer) {
                        //simulating a heavy duty computational expensive operation
                        for (int i = 0; i < 1000000000; i++) {
                        }
                        return multiplyInt(integer, 2);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("flatMap", "onNext: " + integer.toString());
                    }
                });
    }

    private Observable<Integer> multiplyInt(final Integer integer, final int mulplier) {
        //simulating a heavy duty computational expensive operation
        for (int i = 0; i < 1000000000; i++) {
        }
        return Observable.just(new Integer(integer * mulplier));
    }

    public void flapMapFilterList() {

        List<String> alObserver = new ArrayList<>();
        alObserver.add("Cricket");
        alObserver.add("Football");
        alObserver.add("Basketball");

        final Observable<List<String>> aObservable = Observable.just(alObserver);

        aObservable.flatMap(new Function<List<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(List<String> usersList) throws Exception {
                return Observable.fromIterable(usersList); // returning user one by one from usersList.
            }
        })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String user) throws Exception {
                        // filtering user who follows me.
                        return user.equalsIgnoreCase("Basketball");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String str) {
                        System.out.println("Tag Filter " + str);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
