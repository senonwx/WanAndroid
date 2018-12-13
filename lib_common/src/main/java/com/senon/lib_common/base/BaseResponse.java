package com.senon.lib_common.base;

import com.google.gson.annotations.SerializedName;

/**
 * BaseResponse<T>
 */
public class BaseResponse<T> {

    @SerializedName("errorMsg")
    private String msg;
    @SerializedName("errorCode")
    private int code;
    private T data;



    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }


}
