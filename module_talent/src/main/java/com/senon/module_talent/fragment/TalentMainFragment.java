package com.senon.module_talent.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.AppConfig;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.net.cookies.CookiesManager;
import com.senon.lib_common.net.cookies.PersistentCookieStore;
import com.senon.lib_common.utils.ACache;
import com.senon.lib_common.utils.BaseEvent;
import com.senon.lib_common.utils.LogUtils;
import com.senon.module_talent.R;
import com.senon.module_talent.contract.TalentMainFragmentCon;
import com.senon.module_talent.presenter.TalentMainFragmentPre;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * talent mian fragment
 */
public class TalentMainFragment extends BaseLazyFragment<TalentMainFragmentCon.View, TalentMainFragmentCon.Presenter> implements
        TalentMainFragmentCon.View {


    private RelativeLayout collect_lay,login_lay,about_lay,cache_lay;
    private TextView username_tv,login_tv,cache_tv;
    private File cacheFile;

    @Override
    public int getLayoutId() {
        return R.layout.talent_fragment_main;
    }
    @Override
    public TalentMainFragmentCon.Presenter createPresenter() {
        return new TalentMainFragmentPre(mContext);
    }
    @Override
    public TalentMainFragmentCon.View createView() {
        return this;
    }
    @Override
    public void init(View rootView) {
        collect_lay = rootView.findViewById(R.id.collect_lay);
        login_lay = rootView.findViewById(R.id.login_lay);
        about_lay = rootView.findViewById(R.id.about_lay);
        cache_lay = rootView.findViewById(R.id.cache_lay);
        username_tv = rootView.findViewById(R.id.username_tv);
        login_tv = rootView.findViewById(R.id.login_tv);
        cache_tv = rootView.findViewById(R.id.cache_tv);

    }

    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //第一次可见时，自动加载页面
        LogUtils.e("-----> 子fragment进行初始化操作");

        getLoginInfo();
        initViewListener();
    }

    private void initViewListener() {
        cacheFile = new File(AppConfig.PATH_CACHE);
        cache_tv.setText(ACache.getCacheSize(cacheFile));

        collect_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PersistentCookieStore.getCookieStore().isLogin()) {//已经登录过了
                    ARouter.getInstance().build(ConstantArouter.PATH_TALENT_COLLECTIONACTIVITY)
                            .navigation();
                }else{
                    ARouter.getInstance().build(ConstantLoginArouter.PATH_COMMON_LOGINACTIVITY)
                            .navigation();
                }
            }
        });
        about_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConstantArouter.PATH_TALENT_ABOUTACTIVITY)
                        .navigation();
            }
        });
        cache_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACache.deleteDir(cacheFile);
                cache_tv.setText(ACache.getCacheSize(cacheFile));
            }
        });
    }

    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        //之后每次可见的操作
        LogUtils.e("-----> 子fragment每次可见时的操作");

        getLoginInfo();
        cache_tv.setText(ACache.getCacheSize(cacheFile));
    }

    private void getLoginInfo(){
        if(PersistentCookieStore.getCookieStore().isLogin()){//已经登录过了
            username_tv.setText(PersistentCookieStore.getCookieStore().getUsername());
            login_tv.setText("退出");
            login_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CookiesManager.clearAllCookies();//清空缓存
                    getPresenter().getLogout(true,true);

                    sendMsgForLog(1);//退出需要发送eventbus
                }
            });

        }else{
            username_tv.setText("游客");
            login_tv.setText("登录");
            login_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ConstantLoginArouter.PATH_COMMON_LOGINACTIVITY)
                            .navigation();
                }
            });

        }
    }

    private void sendMsgForLog(int code){
        BaseEvent event = new BaseEvent();
        event.setCode(code);
        EventBus.getDefault().post(event);
    }

    @Override
    public void getLogoutResult(BaseResponse data) {
        CookiesManager.clearAllCookies();//清空缓存
        getLoginInfo();
    }


}
