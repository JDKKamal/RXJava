package com.jdkgroup.rxjava2mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jdkgroup.baseclasses.SimpleMVPActivity;
import com.jdkgroup.model.User;
import com.jdkgroup.presenter.RXJava2Presenter;
import com.jdkgroup.rxjava.R;
import com.jdkgroup.utils.AppUtils;
import com.jdkgroup.view.RXJavaView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class RXJava2MVPActivity extends SimpleMVPActivity<RXJava2Presenter, RXJavaView> implements RXJavaView {

    Map paramMap;

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

        getPresenter().callRXJavListDetailApi();
    }

    @Override
    public void onFailure(String message) {
        System.out.println("Tag" + message);
    }

    @Override
    public void responseListUser(List<User> response) {
        System.out.println("Tag List Users " + response.size());
    }
}
