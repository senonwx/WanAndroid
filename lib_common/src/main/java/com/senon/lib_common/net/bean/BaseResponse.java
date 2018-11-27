package com.senon.lib_common.net.bean;

/**
 * BaseResponse<T>
 */
public class BaseResponse<T> {

    private String msg;
    private boolean success;
    private int code;
    private T data;
    private int status;


    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
