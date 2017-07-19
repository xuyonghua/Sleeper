package com.xyh.sleeper.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xyh on 2017/7/7.
 */

public class BaseResponse {
    @SerializedName("error")
    private String errorMsg;
    @SerializedName("results")
    private List<BeautyBean> dataList;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<BeautyBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<BeautyBean> dataList) {
        this.dataList = dataList;
    }
}
