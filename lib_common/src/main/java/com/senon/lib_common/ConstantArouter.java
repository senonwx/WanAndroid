package com.senon.lib_common;

/**
 * 所有模块均依赖commonmoduel  所以路由跳转均写入本Constant方便调用
 * 常量类
 * 其中: 路由跳转命名统一用：path+模块名+Activity名
 */
public class ConstantArouter {

    /**
     * App
     */
    public static final String PATH_APP_MAINACTIVITY = "/app/MainActivity";
    public static final String PATH_APP_FRAGMENTHOMEACTIVITY = "/app/FragmentHomeActivity";


    /**
     * home 主页
     */
    public static final String PATH_HOME_MAINACTIVITY = "/home/MainActivity";
    public static final String PATH_HOME_HOMEARTICLEACTIVITY = "/home/HomeArticleActivity";
    public static final String PATH_HOME_HOMEPROJECTACTIVITY = "/home/HomeProjectActivity";

    /**
     * life 知识体系
     */
    public static final String PATH_LIFE_KNOWLEDGESYSTEMACTIVITY = "/life/KnowledgeSystemActivity";

    /**
     * common
     */
    public static final String PATH_COMMON_REGISTERACTIVITY = "/lib_common/CommonRegisterActivity";
    public static final String PATH_COMMON_WEBVIEWCTIVITY = "/lib_common/CommonWebviewActivity";

    /**
     * talent 个人中心
     */
    public static final String PATH_TALENT_COLLECTIONACTIVITY = "/talent/CollectionActivity";
    public static final String PATH_TALENT_ABOUTACTIVITY = "/talent/AboutActivity";


}
