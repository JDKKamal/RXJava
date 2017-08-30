package com.jdkgroup.presenter;

import com.jdkgroup.baseclasses.BasePresenter;
import com.jdkgroup.connection.RestConstant;
import com.jdkgroup.model.User;
import com.jdkgroup.rxjava.R;
import com.jdkgroup.view.RXJavaView;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RXJava2Presenter extends BasePresenter<RXJavaView> {

    public void callRXJavSingleDetailApi(Map paramMap) {
        if (hasInternet()) { //CHECK THE INTERNET CONNECTION
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

    public void callRXJavListDetailApi() {
        if (hasInternet()) { //CHECK THE INTERNET CONNECTION
            getView().showProgressDialog(true);
            Rx2AndroidNetworking.get(RestConstant.API_GETALLUSER)
                    .addPathParameter("pageNumber", "0")
                    .addQueryParameter("limit", "3")
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
}