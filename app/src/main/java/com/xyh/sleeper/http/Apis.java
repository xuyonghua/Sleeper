package com.xyh.sleeper.http;

import android.util.Log;

/**
 * Created by xyh on 2017/7/6.
 */

public class Apis {
    public static final String BASE_URL = "http://c.m.163.com/";
    // 笑话
    public static final String JOKE_ID = "T1350383429665";

    // 热点视频
    public static final String VIDEO_HOT_ID = "V9LG4B3A0";
    // 娱乐视频
    public static final String VIDEO_ENTERTAINMENT_ID = "V9LG4CHOR";
    // 搞笑视频
    public static final String VIDEO_FUN_ID = "V9LG4E6VR";
    // 精品视频
    public static final String VIDEO_CHOICE_ID = "00850FRB";


    public static final int PAGE_SIZE = 10;

    /**
     * 返回一个随机生成的妹子 Api
     *
     * @return url
     */
    public static String getBeautyApi() {
        StringBuilder girlApi = new StringBuilder();
        double random = Math.random();
        int result = (int) (random * 50 - 20);
        int randomNum = Math.abs(result);
        girlApi.append("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/").append("15").append("/" + randomNum);
        return String.valueOf(girlApi);
    }

    public static String getJokeUrl(int page) {
        StringBuilder jokeApi = new StringBuilder();
        jokeApi.append(BASE_URL).append("nc/article/list/")
                .append(JOKE_ID).append("/").append(page).append("-")
                .append(PAGE_SIZE).append(".html");
        return jokeApi.toString();
    }

    /**
     * http://c.3g.163.com/nc/video/list/V9LG4CHOR/n/10-10.html 视频
     */
    public static String getVideoUrl(int page) {
        StringBuilder videoApi = new StringBuilder();
        videoApi.append(BASE_URL).append("nc/video/list/")
                .append(VIDEO_CHOICE_ID).append("/n/").append(String.valueOf(page * 10)).append("-")
                .append(PAGE_SIZE).append(".html");
        Log.d("VideoFragment","videoApi:" + videoApi.toString());
        return videoApi.toString();
    }

}
