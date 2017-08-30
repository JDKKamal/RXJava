package com.jdkgroup.presenter;

import android.util.Log;

import com.jdkgroup.baseclasses.BasePresenter;
import com.jdkgroup.connection.RestConstant;
import com.jdkgroup.model.User;
import com.jdkgroup.rxjava.R;
import com.jdkgroup.view.RXJavaView;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RXJava2Presenter extends BasePresenter<RXJavaView> {

    public void callRXJavSingleDetailApi(HashMap<String, String>  paramMap) {
        if (hasInternet()) {
            getView().showProgressDialog(true);
            Rx2AndroidNetworking.get(RestConstant.API_GETANUSER)
                    .addPathParameter(paramMap)
                    .build()
                    .getObjectObservable(User.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(User user) {
                            getView().onSuccess(user);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().showProgressDialog(false);
                            getView().onFailure(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            getView().showProgressDialog(false);
                        }
                    });
        } else {
            getView().showProgressDialog(false);
            getView().onFailure(getView().getActivity().getString(R.string.no_internet_message));
        }
    }

    public void callRXJavListDetailApi(HashMap<String, String> paramMap) {
        if (hasInternet()) {
            getView().showProgressDialog(true);
            Rx2AndroidNetworking.get(RestConstant.API_GETALLUSER)
                    .addPathParameter("pageNumber", "0")
                    .addQueryParameter(paramMap)
                    .build()
                    .getObjectListObservable(User.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<User>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            // handle error
                            getView().showProgressDialog(false);
                            getView().onFailure(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            getView().showProgressDialog(false);
                        }

                        @Override
                        public void onNext(List<User> user) {
                            getView().responseListUser(user);
                        }
                    });
        } else {
            getView().showProgressDialog(false);
            getView().onFailure(getView().getActivity().getString(R.string.no_internet_message));
        }
    }

    public Observer<String> getObserverMerge() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                getView().doMerge(value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public Observer<Integer> getObserverDistinct() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                getView().doDistinct(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}