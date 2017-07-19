package com.xyh.sleeper.ui.video;

import com.xyh.sleeper.BaseContract;
import com.xyh.sleeper.entity.VideoResponse;
import com.xyh.sleeper.http.HttpResponse;
import com.xyh.sleeper.http.RetrofitHelper;

/**
 * Created by xyh on 2017/7/14.
 */

public class VideoPresenter implements BaseContract.Presenter{
    private BaseContract.View mView;

    public VideoPresenter(BaseContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void httpRequest(String url, int page) {
        RetrofitHelper.getInstance().getVideoData(url, new HttpResponse<VideoResponse>() {
            @Override
            public void onSuccess(VideoResponse response) {
                mView.onSuccess(response.getVideoList());
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }
        });
    }
}
