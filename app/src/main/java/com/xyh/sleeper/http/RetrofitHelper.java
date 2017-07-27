package com.xyh.sleeper.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xyh.sleeper.entity.BaseResponse;
import com.xyh.sleeper.entity.JokeResponse;
import com.xyh.sleeper.entity.VideoResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xyh on 2017/6/17 .
 */
public class RetrofitHelper {

    public static final int DEFAULT_TIMEOUT = 5;

    private HttpService httpService;

    Retrofit retrofit;

    private static RetrofitHelper mRetrofitHelper = null;

    private CompositeDisposable compositeDisposable;

    private RetrofitHelper() {
        init();
    }

    public static RetrofitHelper getInstance() {
        synchronized (RetrofitHelper.class) {
            if (mRetrofitHelper == null) {
                mRetrofitHelper = new RetrofitHelper();
            }
        }
        return mRetrofitHelper;
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .baseUrl(Apis.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpService.class);
        compositeDisposable = new CompositeDisposable();
    }


    public void getBeautyData(String url, final HttpResponse<BaseResponse> response) {
        Observable<BaseResponse> observable = httpService.getBeautyData(url);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse value) {
                        response.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        response.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getJokeData(String url, final HttpResponse<JokeResponse> response) {
        Observable<JokeResponse> observable = httpService.getJokeList(url);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JokeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JokeResponse value) {
                        response.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        response.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getVideoData(String url, final HttpResponse<VideoResponse> response) {
        Observable<VideoResponse> observable = httpService.getVideoData(url);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(VideoResponse value) {
                        response.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        response.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancelAll(){
        compositeDisposable.clear();
    }

}
