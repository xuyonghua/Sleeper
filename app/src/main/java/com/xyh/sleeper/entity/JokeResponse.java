package com.xyh.sleeper.entity;

import com.google.gson.annotations.SerializedName;
import com.xyh.sleeper.http.Apis;

import java.util.List;

/**
 * Created by xyh on 2017/7/12.
 */

public class JokeResponse {
    @SerializedName(Apis.JOKE_ID)
    List<JokeBean> jokeList;

    public List<JokeBean> getJokeList() {
        return jokeList;
    }

    public void setJokeList(List<JokeBean> jokeList) {
        this.jokeList = jokeList;
    }
}
