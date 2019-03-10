package com.senon.lib_common.base;

import android.app.Application;
import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.senon.lib_common.R;
import com.senon.lib_common.net.cookies.PersistentCookieStore;
import com.senon.lib_common.service.InitializeService;
import com.senon.lib_common.utils.ConstantUtils;
import com.senon.lib_common.utils.LogUtils;
import com.senon.lib_common.utils.PreferenceTool;
import com.senon.lib_common.utils.ToastUtil;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;


public class BaseAppDeletage {

    private Application mApplication;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                // 指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public BaseAppDeletage(Application application) {
        mApplication = application;
    }

    public void onCreate() {
        ConstantUtils.init(mApplication);     //全局Utils
        LogUtils.setLogEnable(ConstantUtils.isAppDebug());  //Log日志
        PreferenceTool.init(mApplication);   //Preference参数
        PersistentCookieStore.init(mApplication);   //Cookies持久化Preference参数
        ToastUtil.init(mApplication);       //吐司初始化
        initAutoSizeUnits();                 //配置全局 布局适配单位mm

        InitializeService.start(mApplication);   //初始化服务Service
    }

    private void initAutoSizeUnits() {
        AutoSizeConfig.getInstance().getUnitsManager()
                //支持dp适配 默认true
                .setSupportDP(false)
                //支持sp适配 默认true
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
        AutoSizeConfig.getInstance()
                //按照宽度适配 默认true
                .setBaseOnWidth(true)
                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(true);
    }
}
