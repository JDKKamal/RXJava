package com.jdkgroup.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jdkgroup.rxjava.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FlatMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flapMap();
    }

    private void flapMap() {
        Observable.just(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(integer -> {
                    Log.d("flatMap", integer + " * 2");
                    return multiplyInt(integer, 2);
                })
                .flatMap(integer -> {
                    Log.d("flatMap", integer + " * 3");
                    return multiplyInt(integer, 3);
                })
                .flatMap(new Function<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> apply(Integer integer) {
                        Log.d("flatMap", integer + " * 4");
                        return multiplyInt(integer, 4);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("flatMap", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("flatMap", "onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("flatMap", "Final result: " + integer.toString());
                    }
                });

    }

    private Observable<Integer> multiplyInt(final Integer integer, final int mulplier) {
        //simulating a heavy duty computational expensive operation
        for (int i=0; i<1000000000; i++) {}
        return Observable.just(new Integer(integer * mulplier));
    }
}
