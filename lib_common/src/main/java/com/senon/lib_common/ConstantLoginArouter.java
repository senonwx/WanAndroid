package com.senon.lib_common;

import java.util.HashMap;
import java.util.Map;

/**
 * 判断是某个模块登录的
 */
public class ConstantLoginArouter {

    public static Map<String, String> activityRouterMap = new HashMap<>();

    //app主页
    public static final String PATH_APP_MAINACTIVITY = "/app/MainActivity";

    //home主页
    public static final String PATH_HOME_MAINACTIVITY = "/home/HomeMainActivity";
    //修生活首页
    public static final String PATH_LIFE_MAINACTIVITY = "/life/LifeMainActivity";
    //秀才艺首页
    public static final String PATH_ART_MAINACTIVITY = "/art/ArtMainActivity";
    //秀文采首页
    public static final String PATH_TALENT_MAINACTIVITY = "/talent/TalentMainActivity";
    //秀圈子首页
    public static final String PATH_GROUP_MAINACTIVITY = "/group/GroupMainActivity";
    //IM首页
    public static final String PATH_IM_MAINACTIVITY = "/im/IMMainActivity";

    //登录
    public static final String PATH_COMMON_LOGINACTIVITY = "/lib_common/CommonLoginActivity";

    static {
        activityRouterMap.put(getActivityName(PATH_APP_MAINACTIVITY), PATH_APP_MAINACTIVITY);
        activityRouterMap.put(getActivityName(PATH_HOME_MAINACTIVITY), PATH_HOME_MAINACTIVITY);
        activityRouterMap.put(getActivityName(PATH_LIFE_MAINACTIVITY), PATH_LIFE_MAINACTIVITY);
        activityRouterMap.put(getActivityName(PATH_ART_MAINACTIVITY), PATH_ART_MAINACTIVITY);
        activityRouterMap.put(getActivityName(PATH_TALENT_MAINACTIVITY), PATH_TALENT_MAINACTIVITY);
        activityRouterMap.put(getActivityName(PATH_GROUP_MAINACTIVITY), PATH_GROUP_MAINACTIVITY);
        activityRouterMap.put(getActivityName(PATH_IM_MAINACTIVITY), PATH_IM_MAINACTIVITY);
        activityRouterMap.put(getActivityName(PATH_COMMON_LOGINACTIVITY), PATH_COMMON_LOGINACTIVITY);
    }

    private static String getActivityName(String routerUrl) {
        int pos = routerUrl.lastIndexOf("/");
        return routerUrl.substring(pos + 1);
    }

    public static String getCurRouter(String activityName) {
        return activityRouterMap.get(activityName);
    }
}
