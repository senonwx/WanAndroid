package com.senon.module_home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.module_home.fragment.HomeMainFragment;

/**
 * home 首页
 */
@Route(path = ConstantLoginArouter.PATH_HOME_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentManager fragmentManager;//声明fragment管理
    private HomeMainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);
        ARouter.getInstance().inject(this);

        findView();
    }

    private void findView() {
        viewPager = findViewById(R.id.home_main_vp);
        fragmentManager = getSupportFragmentManager();
        fragment = new HomeMainFragment();
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
