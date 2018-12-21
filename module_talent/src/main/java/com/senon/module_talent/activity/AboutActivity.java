package com.senon.module_talent.activity;


import android.content.pm.PackageManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.base.BaseActivity;
import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.utils.StatusBarUtils;
import com.senon.module_talent.R;

/**
 * 关于 页面
 */
@Route(path = ConstantArouter.PATH_TALENT_ABOUTACTIVITY)
public class AboutActivity extends BaseActivity<BaseViewImp,BasePresenter<BaseViewImp>> implements
        BaseViewImp{

    private TextView version_tv,content_tv,toolbar_title_tv;


    @Override
    public int getLayoutId() {
        StatusBarUtils.with(this).init();
        return R.layout.talent_activity_about;
    }
    @Override
    public BasePresenter createPresenter() {
        return getPresenter();
    }
    @Override
    public BaseViewImp createView() {
        return this;
    }

    @Override
    public void init() {
        ComUtil.changeStatusBarTextColor(this,true);

        version_tv = findViewById(R.id.version_tv);
        content_tv = findViewById(R.id.content_tv);
        toolbar_title_tv = findViewById(R.id.toolbar_title_tv);
        toolbar_title_tv.setText("关于");

        content_tv.setText(Html.fromHtml(getString(R.string.talent_about_content)));
        content_tv.setMovementMethod(LinkMovementMethod.getInstance());
        try {
            String versionStr = getString(R.string.talent_wanandroid)
                    + "  V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            version_tv.setText(versionStr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        findViewById(R.id.toolbar_back_igv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
