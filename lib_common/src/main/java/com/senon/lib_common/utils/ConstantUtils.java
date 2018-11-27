package com.senon.lib_common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * 基础常量工具类
 */
public class ConstantUtils {
    private static Context context;
    /**
     * 初始化工具类
     * @param context 上下文
     */
    public static void init(Context context) {
        ConstantUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     * @return ApplicationContext
     */
    public static Context getAPPContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    /**
     * 判断App是否是Debug版本
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        String packageName = context.getPackageName();
        if (packageName == null || packageName.trim().length() == 0)
            return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
