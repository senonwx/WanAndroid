package com.senon.lib_common.utils;

import android.util.Log;

import java.util.Locale;

/**
 * 打印log日志工具类
 */
public class LogUtils {
    private static boolean LOG = true;
    private static boolean LOGV = true;
    private static boolean LOGD = true;
    private static boolean LOGI = true;
    private static boolean LOGW = true;
    private static boolean LOGE = true;

    public static void setLogEnable(boolean enable) {
        LOG = enable;
    }

    public static void v(String mess) {
        if (LOG && LOGV) {
            Log.v(getTag(), buildMessage(mess));
        }
    }

    public static void d(String mess) {
        if (LOG && LOGD) {
            Log.d(getTag(), buildMessage(mess));
        }
    }

    public static void i(String mess) {
        if (LOG && LOGI) {
            Log.i(getTag(), buildMessage(mess));
        }
    }

    public static void w(String mess) {
        if (LOG && LOGW) {
            Log.w(getTag(), buildMessage(mess));
        }
    }

    public static void e(String mess) {
        if (LOG && LOGE) {
            log(getTag(), buildMessage(mess));
        }
    }

    public static void log(String tag, String msg){
        if(msg.length() > 4000) {
            for(int i=0;i<msg.length();i+=4000){
                if(i+4000<msg.length())
                    Log.e(tag,msg.substring(i, i+4000));
                else
                    Log.e(tag,msg.substring(i, msg.length()));
            }
        } else
            Log.e(tag,msg);
    }

    private static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtils.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtils.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread()
                .getId(), caller, msg);
    }

}
