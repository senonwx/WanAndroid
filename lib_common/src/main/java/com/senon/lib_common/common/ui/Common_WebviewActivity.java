package com.senon.lib_common.common.ui;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.R;
import com.senon.lib_common.base.BaseActivity;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.common.contract.WebviewContract;
import com.senon.lib_common.common.presenter.WebviewPresenter;
import com.senon.lib_common.utils.BaseEvent;
import com.senon.lib_common.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * webview
 */
@Route(path = ConstantArouter.PATH_COMMON_WEBVIEWCTIVITY)
public class Common_WebviewActivity extends BaseActivity<WebviewContract.View, WebviewContract.Presenter> implements
        WebviewContract.View {

    @Autowired
    int id;
    @Autowired
    String url;
    @Autowired
    boolean isCollection;
    @Autowired
    String title;
    private TextView toolbar_title_tv;
    private ProgressBar progressBar;
    private WebView webView;
    private TextView collection_tv;


    @Override
    public int getLayoutId() {
        StatusBarUtils.with(this).init();
        return R.layout.activity_common__webview;
    }

    @Override
    public WebviewContract.Presenter createPresenter() {
        return new WebviewPresenter(this);
    }

    @Override
    public WebviewContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        ComUtil.changeStatusBarTextColor(this,true);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);
        collection_tv = findViewById(R.id.collection_tv);
        toolbar_title_tv = findViewById(R.id.toolbar_title_tv);

        initClickListener();
        initWebView();
    }

    private void initClickListener() {
        toolbar_title_tv.setText(title);
        collection_tv.setText(isCollection ? "已收藏":"收藏");
        collection_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCollection = !isCollection;//该为相反状态
                collection_tv.setText(isCollection ? "已收藏":"收藏");
                if(isCollection){
                    getPresenter().getCollect(id,false,true);
                }else{
                    getPresenter().getUnollect(id,false,true);
                }
            }
        });
        toolbar_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.scrollTo(0,0);
            }
        });
        findViewById(R.id.toolbar_back_igv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextZoom(100);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadUrl(url);
//        webView.addJavascriptInterface(javaScript, "Android");

        webView.setWebViewClient(new WebViewClient() {
            //实现点击webView页面的链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        //设置WebViewClient用来辅助WebView处理各种通知请求事件等，如更新历史记录、网页开始加载/完毕、报告错误信息等
        webView.setWebViewClient(new WebViewClient() {
            // 以下方法避免 自动打开系统自带的浏览器，而是让新打开的网页在当前的WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 用于辅助WebView处理JavaScript的对话框、网站图标、网站标题以及网页加载进度等
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (progressBar.getVisibility() == View.INVISIBLE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }
            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
            }
        });

    }

    @Override
    public void getCollectResult(int id,boolean isCollect) {
        BaseEvent event = new BaseEvent();
        event.setCode(101);
        event.setId(id);
        event.setCollect(isCollect);
        EventBus.getDefault().post(event);
    }

    @Override
    public void getCollFailResult(int id) {
        if(this.id == id){
            isCollection = !isCollection;
            collection_tv.setText(isCollection ? "已收藏":"收藏");
        }
        ARouter.getInstance().build(ConstantLoginArouter.PATH_COMMON_LOGINACTIVITY)
                .navigation();
    }
}
