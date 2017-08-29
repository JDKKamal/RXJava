package com.jdkgroup.presenter;

import android.content.Context;

import com.jdkgroup.baseclasses.BasePresenter;
import com.jdkgroup.connection.RestConstant;
import com.jdkgroup.model.User;
import com.jdkgroup.rxjava.R;
import com.jdkgroup.view.RXJavaView;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RXJava2Presenter extends BasePresenter<RXJavaView> {

    public void callRXJavSingleDetailApi(final Context context) {

        if (hasInternet()) { //CHECK THE INTERNET CONNECTION
            getView().showProgressDialog(true);
            Rx2AndroidNetworking.get(RestConstant.API_GETANUSER)
                    .addPathParameter("userId", "1")
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
                            getView().showProgressDialog(false);
                            getView().onSuccess(user);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().onFailure(e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            getView().onFailure(context.getString(R.string.no_internet_message));
        }
    }
}
