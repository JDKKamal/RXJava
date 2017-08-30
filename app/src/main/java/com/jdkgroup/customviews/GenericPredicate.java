package com.jdkgroup.customviews;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

/**
 * Created by kamlesh on 8/30/2017.
 */

public class GenericPredicate<T> implements Predicate<T> {
    T varc1;

    public boolean test(T varc) {
        if (varc1.equals(varc)) {
            return true;
        }
        return false;
    }

    class LoopConsumer<T> implements Consumer<T> {
        public void accept(T ctask) {
            System.out.println("Processing Task " + ctask);
        }
    }

    /*public void OutputLoopConsumer() {
        List<Integer> myList = new ArrayList<>();
        LoopConsumer<Integer> mcons = new LoopConsumer<Integer>();

        myList.add(100);
        myList.add(200);
        myList.add(300);

        myList.forEach(mcons);
    }*/
}