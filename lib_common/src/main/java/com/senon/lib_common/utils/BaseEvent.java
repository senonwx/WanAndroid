package com.senon.lib_common.utils;

/**
 * eventbus发送实体
 */
public class BaseEvent<T> {

    /**
     * -1:退出登录时通知Mainactivity finish
     * 0：AutoLoginService 自动登录成功    刷新秀生活界面点赞功能 / 通知龙门阵模块登录腾讯IM
     * 1：AutoLoginService 自动登录失效
     *
     * 101:文章收藏/取消收藏  （HomeMainFragment、ArtMainFragment、HomeArticleActivity、HomeProjectActivity）
     *
     *
     *
     *
     *
     */
    private int code;
    private String msg;
    private T data;
    private boolean isCollect;
    private int id;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
