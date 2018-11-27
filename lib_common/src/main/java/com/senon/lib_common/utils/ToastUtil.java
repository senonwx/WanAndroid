package com.senon.lib_common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.senon.lib_common.R;


/**
 * Toast工具类
 */
public class ToastUtil {

    /**
     * 可以在多线程里运行的toast
     */
    private static volatile Toast mToast;
    private static final Object lock = new Object();
    private static Context context;
    private static TextView toastTv;

    public static void init(Context con){
        context = con;
    }

    public static void initToast(String msg) {
        initToast(msg, context, true);
    }

    public static void initToast(int resId, Context context) {
        if (context instanceof Activity || context instanceof FragmentActivity)
            context = context.getApplicationContext();
        initToast(context.getString(resId), context, true);
    }

    public static void initToast(String msg, Context context, boolean isSingleton) {
        if (context instanceof Activity || context instanceof FragmentActivity)
            context = context.getApplicationContext();
        if (mToast != null && isSingleton) {
            mToast.setText(msg);
        } else {
            synchronized (lock) {
//                mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                mToast = showToast(context,msg,Toast.LENGTH_SHORT);
            }
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);//默认显示位置
        mToast.show();
    }
    //可以设置toast的位置
    public static void setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
    }
    //可以自定义toast的view
    public void setView(View view) {
        mToast.setView(view);
    }


    /**
      当你在线程中使用toast时，请使用这个方法(可以控制显示多长时间)
    */
    public static void showInThread(@NonNull final Context context, final String msg, final int length) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();//先移除
                Toast.makeText(context, msg, length).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }.start();
    }

    /**
     * 以下全部代码为一个整体，可以控制显示时间的Toast
     */
    private static Handler mHandler = null;
    private static int duration = 0;
    private static int currDuration = 0;
    private static final int DEFAULT = 2000;

    public static void showByDuration(Context context, String msg, int duration) {
        duration = duration;
        currDuration = DEFAULT;
//        mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        mToast = showToast(context,msg,Toast.LENGTH_LONG);
        mHandler = new Handler(context.getMainLooper());
        mHandler.post(mToastThread);
    }

    private static Runnable mToastThread = new Runnable() {
        public void run() {
            mToast.show();
            mHandler.postDelayed(mToastThread, DEFAULT);// 每隔2秒显示一次
            if (duration != 0) {
                if (currDuration <= duration) {
                    currDuration += DEFAULT;
                } else {
                    cancel();
                }
            }
        }
    };

    private static void cancel() {
        mHandler.removeCallbacks(mToastThread);// 先把显示线程删除
        mToast.cancel();// 把最后一个线程的显示效果cancel掉，就一了百了了
        currDuration = DEFAULT;
    }


    private static Toast showToast(Context context, String msg, int duration) {
        if (null != context) {
            if (mToast == null) {
                mToast = new Toast(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.toast_layout, null);
                toastTv = layout.findViewById(R.id.message);
                toastTv.setText(msg);
                mToast.setDuration(duration);
                mToast.setView(layout);
            }else {
                toastTv.setText(msg);
            }
        }
        return mToast;
    }

}
