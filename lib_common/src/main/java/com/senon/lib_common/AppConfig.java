package com.senon.lib_common;

import com.senon.lib_common.utils.ConstantUtils;

/**
 * APP配置参数
 */
public class AppConfig {


    public static final String BASE_URL = "http://www.wanandroid.com/";

    public static final String PATH_DATA = ConstantUtils.getAPPContext().getCacheDir().getAbsolutePath()+"/"+"data";

    public static final String PATH_CACHE = PATH_DATA+"/"+"Cache";

}
