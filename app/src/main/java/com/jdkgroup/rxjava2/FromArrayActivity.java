package com.jdkgroup.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jdkgroup.model.AppInfo;
import com.jdkgroup.rxjava.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
public class FromArrayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<AppInfo> listAppInfo = new ArrayList<>();
        listAppInfo.add(new AppInfo(1, "kamal"));
        listAppInfo.add(new AppInfo(1, "kamal"));
        loadList(listAppInfo);
    }

    private void loadList(List<AppInfo> apps) {
        Observable.fromArray(apps)
                .subscribe(new Observer<List<AppInfo>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(List<AppInfo> appInfo) {
                        System.out.println("Tag" + appInfo.size());
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
