package com.xyh.sleeper.http;


import com.xyh.sleeper.entity.BaseResponse;
import com.xyh.sleeper.entity.JokeResponse;
import com.xyh.sleeper.entity.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * Created by xyh on 2017/6/15.
 */
public interface HttpService {
    @Headers("User-Agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    @GET
    Observable<JokeResponse> getJokeList(@Url String url);

    @Headers("User-Agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    @GET
    Observable<VideoResponse> getVideoData(@Url String url);

    @GET
    Observable<BaseResponse> getBeautyData(@Url String url);
}
