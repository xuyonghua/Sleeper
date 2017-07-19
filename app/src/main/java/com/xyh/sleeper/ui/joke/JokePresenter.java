package com.xyh.sleeper.ui.joke;

import android.util.Log;

import com.xyh.sleeper.BaseContract;
import com.xyh.sleeper.entity.JokeResponse;
import com.xyh.sleeper.http.HttpResponse;
import com.xyh.sleeper.http.RetrofitHelper;

/**
 * Created by xyh on 2017/7/14.
 */

public class JokePresenter implements BaseContract.Presenter {
    private static final String TAG = "JokePresenter";

    private BaseContract.View view;

    public JokePresenter(BaseContract.View view) {
        this.view = view;
    }

    @Override
    public void httpRequest(String url, int page) {
        Log.d(TAG, "httpRequest: " + url);
        RetrofitHelper.getInstance().getJokeData(url, new HttpResponse<JokeResponse>() {
            @Override
            public void onSuccess(JokeResponse response) {
                view.onSuccess(response.getJokeList());
            }

            @Override
            public void onError(Throwable e) {
                view.onError(e.getMessage());
            }
        });
    }
}
