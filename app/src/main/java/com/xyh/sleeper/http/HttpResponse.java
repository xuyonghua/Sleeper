package com.xyh.sleeper.http;

/**
 * Created by xyh on 2017/7/10.
 */

public interface HttpResponse<T> {
    void onSuccess(T response);

    void onError(Throwable e);
}
