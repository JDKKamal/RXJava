package com.jdkgroup.view;

import com.jdkgroup.baseclasses.BaseView;
import com.jdkgroup.model.User;

import java.util.List;

public interface RXJavaView extends BaseView<User> {
    void responseListUser(List<User> response);

    void doMerge(String str);

    void doDistinct(Integer value);
}

