package com.senon.lib_common.utils;

/**
 * eventbus发送实体
 */
public class BaseEvent<T> {

    /**
     * -1:退出登录时通知Mainactivity finish
     * 0：登录成功   刷新所有主界面数据列表
     * 1：退出成功   。。。
     *
     * 101:文章收藏/取消收藏  （HomeMainFragment、ArtMainFragment、HomeArticleActivity、HomeProjectActivity）
     * 102:文章收藏/取消收藏失败 。。。
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
    private boolean ingored;


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

    public boolean isIngored() {
        return ingored;
    }

    public void setIngored(boolean ingored) {
        this.ingored = ingored;
    }


}
