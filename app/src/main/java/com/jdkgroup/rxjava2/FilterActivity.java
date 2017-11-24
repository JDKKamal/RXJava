package com.jdkgroup.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.jdkgroup.rxjava.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FilterActivity extends AppCompatActivity {

    private AppCompatTextView appTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appTv = findViewById(R.id.appTv);

        doSomeWork();
    }

    private void doSomeWork() {
        Observable.just("1", "2")
                .filter(s -> s.equals("1"))
                .subscribe(getObserver());
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                appTv.append(" value : " + value + "\n");

            }

            @Override
            public void onError(Throwable e) {
                appTv.append(" onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                appTv.append(" onComplete");
            }
        };
    }
}
