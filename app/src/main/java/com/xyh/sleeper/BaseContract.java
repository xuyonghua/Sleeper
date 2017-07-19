package com.xyh.sleeper;

import java.util.List;

/**
 * Created by xyh on 2017/7/14.
 */

public class BaseContract {
    public interface View<T> {
        void onSuccess(List<T> response);

        void onError(String errorMsg);
    }

    public interface Presenter {
        void httpRequest(String url, int page);
    }

    public interface Model {

    }
}
