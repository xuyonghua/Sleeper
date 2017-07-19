package com.xyh.sleeper.ui.beauty;

import com.xyh.sleeper.BaseContract;
import com.xyh.sleeper.entity.BaseResponse;
import com.xyh.sleeper.http.HttpResponse;
import com.xyh.sleeper.http.RetrofitHelper;

/**
 * Created by xyh on 2017/7/10.
 */

public class BeautyPresenter implements BaseContract.Presenter{
    private BaseContract.View mView;

    public BeautyPresenter(BaseContract.View mView){
        this.mView = mView;
    }
    @Override
    public void httpRequest(String url,int page) {
        RetrofitHelper.getInstance().getBeautyData(url, new HttpResponse<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                mView.onSuccess(response.getDataList());
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }
        });
    }
}
