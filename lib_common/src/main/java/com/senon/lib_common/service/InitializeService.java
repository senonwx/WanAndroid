package com.senon.lib_common.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * 在Service中 初始化耗时的SDK等（BaseAppDeletage调用）
 * 为了在application中 不进行耗时操作 影响冷启动 白屏时间增加
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT = "initApplication";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {

                initApplication();
            }
        }
    }

    //初始化
    private void initApplication() {

    }

}


