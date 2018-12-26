package com.senon.module_art;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.utils.ConstantUtils;
import com.senon.lib_common.utils.LogUtils;
import com.senon.lib_common.utils.StatusBarUtils;
import com.senon.module_art.fragment.ArtMainFragment;

/**
 * 公众号 art fragment
 */
@Route(path = ConstantLoginArouter.PATH_ART_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentManager fragmentManager;//声明fragment管理
    private ArtMainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.with(this).init();
        setContentView(R.layout.art_activity_main);
        ComUtil.changeStatusBarTextColor(this,true);

        ARouter.getInstance().inject(this);
        LogUtils.e("------->isAppDebug=====  "+ ConstantUtils.isAppDebug());
        findView();

    }

    private void findView() {
        viewPager = findViewById(R.id.art_main_vp);
        fragmentManager = getSupportFragmentManager();
        fragment = new ArtMainFragment();
        viewPager.setAdapter(new MyFragmentPagerAdapter(fragmentManager));

    }


    //FragmentPagerAdapter
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragment;
        }
        @Override
        public int getCount() {
            return 1;
        }
    }

}
