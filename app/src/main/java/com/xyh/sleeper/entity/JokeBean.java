package com.xyh.sleeper.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by xyh on 2017/7/11.
 */

public class JokeBean {
    private String title;
    private String digest;
    @SerializedName("imgsrc")
    private String img;
    @SerializedName("imgextra")
    private ArrayList<Imgextra> images;
    private String source;
    @SerializedName("ptime")
    private String time;
    @SerializedName("url_3w")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ArrayList<Imgextra> getImages() {
        return images;
    }

    public void setImages(ArrayList<Imgextra> images) {
        this.images = images;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public class Imgextra{
        public String imgsrc;
    }
}
