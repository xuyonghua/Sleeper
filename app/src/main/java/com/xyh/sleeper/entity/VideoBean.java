package com.xyh.sleeper.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xyh on 2017/7/13.
 */

public class VideoBean {
    private String topicImg;
    private String mp4Hd_url;
    private String title;
    private String cover;
    private String topicName;
    private int playCount;
    @SerializedName("votecount")
    private int voteCount;

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getMp4Hd_url() {
        return mp4Hd_url;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
