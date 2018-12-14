package com.senon.module_life;

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
import com.senon.lib_common.utils.StatusBarUtils;
import com.senon.module_life.fragment.LifeMainFragment;

/**
 * life 首页
 */
@Route(path = ConstantLoginArouter.PATH_LIFE_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentManager fragmentManager;//声明fragment管理
    private LifeMainFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.with(this).init();
        setContentView(R.layout.life_activity_main);
        ComUtil.changeStatusBarTextColor(this,true);
        ARouter.getInstance().inject(this);
        findView();
    }

    private void findView() {
        viewPager = findViewById(R.id.life_main_vp);
        fragmentManager = getSupportFragmentManager();
        fragment = new LifeMainFragment();
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
