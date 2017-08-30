package com.jdkgroup.rxjava2mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jdkgroup.baseclasses.SimpleMVPActivity;
import com.jdkgroup.model.User;
import com.jdkgroup.presenter.RXJava2Presenter;
import com.jdkgroup.rxjava.R;
import com.jdkgroup.utils.AppUtils;
import com.jdkgroup.view.RXJavaView;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RXJava2MVPActivity extends SimpleMVPActivity<RXJava2Presenter, RXJavaView> implements RXJavaView {

    private HashMap<String, String> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        init();
        APICall();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @NonNull
    @Override
    public RXJava2Presenter createPresenter() {
        return new RXJava2Presenter();
    }

    @NonNull
    @Override
    public RXJavaView attachView() {
        return this;
    }

    @Override
    public void onSuccess(User response) {
        AppUtils.showToast(RXJava2MVPActivity.this, "Successful get response");
        System.out.println("Tag " + response.getId());
        System.out.println("Tag " + response.getFirstname());
        System.out.println("Tag " + response.getLastname());
    }

    private void APICall() {
        paramMap = new HashMap();
        paramMap.put("userId", "1");
        getPresenter().callRXJavSingleDetailApi(paramMap);

        paramMap = new HashMap();
        paramMap.put("limit", "3");
        getPresenter().callRXJavListDetailApi(paramMap);

        getFromIterable();
        getPredicate();
        doMerge();
        doDistinct();
        getJustList();
        getFlapMap();


    }

    @Override
    public void onFailure(String message) {
        System.out.println("Tag" + message);
    }

    @Override
    public void responseListUser(List<User> response) {
        System.out.println("Tag List Users " + response.size());
    }

    @Override
    public void doMerge(String str) {
        System.out.println("Tag Merge " + str);
    }

    @Override
    public void doDistinct(Integer value) {
        System.out.println("Tag Distinct " + value);
    }

    private void doMerge() {
        final String[] aStrings = {"A1", "A2", "A3", "A4"};
        final String[] bStrings = {"B1", "B2", "B3"};

        final Observable<String> aObservable = Observable.fromArray(aStrings);
        final Observable<String> bObservable = Observable.fromArray(bStrings);

        Observable.merge(aObservable, bObservable).subscribe(getPresenter().getObserverMerge());
    }

    private Observable<Integer> getObservableDistinct() {
        List<Integer> alDistinct = new ArrayList<>();
        alDistinct.add(1);
        alDistinct.add(1);
        alDistinct.add(2);
        alDistinct.add(3);
        alDistinct.add(3);
        alDistinct.add(4);
        return Observable.fromIterable(alDistinct);
    }

    private void doDistinct() {
        getObservableDistinct()
                .distinct()
                .subscribe((getPresenter().getObserverDistinct()));
    }

    public void getJustList() {
        List<String> list = Arrays.asList("Android", "Ubuntu", "Mac OS");
        Observable<List<String>> listObservable = Observable.fromArray(list);
        listObservable.subscribe(new Observer<List<String>>() {

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> list) {
                System.out.println("Tag Just List " + list.size());
            }
        });
    }

    private void getFlapMap() {
        List<Integer> ints = new ArrayList<>();

        ints.add(new Integer(1));
        ints.add(new Integer(2));
        ints.add(new Integer(1));

        Observable.just(ints)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> apply(List<Integer> ints) {
                        return Observable.fromIterable(ints);
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
                    public void onNext(Integer value) {
                        System.out.println("Tag FlatMap " + value);
                    }
                });
    }

    private void getFromIterable() {
        List<String> versionList = Arrays.asList("Marshmallow", "Lolipop", "Kitkat", "Jelly Bean");
        Observable.fromIterable(versionList)
                .forEach(s -> System.out.println("Tag FromIterable " + s));
    }

    public void getPredicate() {
        Observable<String> observable = Observable.just("java", "hibernate", "android");
        Single<Boolean> itemsContainA = observable.all(new Predicate<String>() {
            @Override
            public boolean test(String t) throws Exception {
                return t.contains("a") ? true : false;
            }
        });
        itemsContainA.subscribe(s -> System.out.println("Tag Predicate " + s));
    }
}
