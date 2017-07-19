package com.xyh.sleeper.entity;

import com.google.gson.annotations.SerializedName;
import com.xyh.sleeper.http.Apis;

import java.util.List;

/**
 * Created by xyh on 2017/7/13.
 */

public class VideoResponse {
    @SerializedName(Apis.VIDEO_CHOICE_ID)
    List<VideoBean> videoList;

    public List<VideoBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoBean> videoList) {
        this.videoList = videoList;
    }
}
