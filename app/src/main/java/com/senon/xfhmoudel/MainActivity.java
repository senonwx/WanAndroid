package com.senon.xfhmoudel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.ConstantLoginArouter;

/**
 * app 模块主页面
 */
@Route(path = ConstantLoginArouter.PATH_APP_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void toA(View view){
//        Toast.makeText(this,"toA",Toast.LENGTH_SHORT).show();
        // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
//        ARouter.getInstance().build(ConstantLoginArouter.PATH_FIRST_MAINACTIVITY).navigation();

//        String curUrl = ConstantLoginArouter.getCurRouter(this.getClass().getSimpleName());
        String curUrl = ConstantLoginArouter.getCurRouter("HomeMainActivity");
        ARouter.getInstance().build(ConstantLoginArouter.PATH_COMMON_LOGINACTIVITY)//指定跳到那个页面
                .withString("targetUrl",ConstantArouter.PATH_APP_FRAGMENTHOMEACTIVITY)//传入目标页面路由地址  可以在指定页面跳入到目标页面
                .navigation();


//        Uri testUriMix = Uri.parse("router://com.senon.firstmoduel/firstmoduel/firstmainactivity");
//        ARouter.getInstance().build(testUriMix)
//                .withString("key1", "value1")
//                .navigation();
    }

    public void toB(View view){
//        Toast.makeText(this,"toB",Toast.LENGTH_SHORT).show();
        ARouter.getInstance().build(ConstantArouter.PATH_APP_FRAGMENTHOMEACTIVITY)
                .navigation();
    }
}

