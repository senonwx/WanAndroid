package com.senon.xfhmoudel;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hanks.htextview.base.HTextView;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.utils.LogUtils;
import com.senon.lib_common.utils.StatusBarUtils;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * app 闪屏页
 */
@Route(path = ConstantArouter.PATH_APP_SPLASHACTIVITY)
public class SplashActivity extends AppCompatActivity {

    private final int count = 5;//count秒后跳过
    private TextView version_tv,timmer_tv;
    private Disposable disposable;
    private HTextView anim_tv1, anim_tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.with(this).init();
        setContentView(R.layout.activity_splash);
        ComUtil.changeStatusBarTextColor(this,true);

        version_tv = findViewById(R.id.version_tv);
        timmer_tv = findViewById(R.id.timmer_tv);
        anim_tv1 = findViewById(R.id.anim_tv1);
        anim_tv2 = findViewById(R.id.anim_tv2);

        try {
            version_tv.setText("版本 V" + getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version_tv.setText("");
        }

        timmer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(disposable != null){
                    disposable.dispose();
                }
                gotoMainAct();
            }
        });

        initTimer();

    }

    private void initTimer() {
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count+1) //设置发送count+1次 因为是count~0秒
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        LogUtils.d("apply"+(count-aLong));
                        return count-aLong; //发送倒计时
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {// 观察者订阅时调用
                    @Override
                    public void accept(Disposable disposable){
                        timmer_tv.setEnabled(true);//在发送数据的时候设置为可以跳过
                        timmer_tv.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//操作UI要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("onCompleted: "+System.currentTimeMillis());
                        timmer_tv.setEnabled(false);

                        gotoMainAct();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        timmer_tv.setText(count + " 跳过");

                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) { //接受到一条就是会操作一次UI
                        LogUtils.d("onNext: "+aLong);

                        timmer_tv.setText(aLong + " 跳过");

                        initHTextView(aLong);
                    }
                });
    }


    private void initHTextView(Long aLong) {
        if(aLong == (2 * count / 3) + 1){//显示第一排文字的时间
            anim_tv1.animateText("Talk is cheap.");
        }else if(aLong == count / 2 +1){//显示第二排文字的时间
            anim_tv2.animateText("Show me the code!");
        }
    }

    private void gotoMainAct(){
        ARouter.getInstance().build(ConstantArouter.PATH_APP_FRAGMENTHOMEACTIVITY)//指定跳到那个页面
                .navigation();
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
    }
}

