package com.senon.module_talent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.module_talent.fragment.TalentMainFragment;

/**
 * home 首页
 */
@Route(path = ConstantLoginArouter.PATH_TALENT_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentManager fragmentManager;//声明fragment管理
    private TalentMainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talent_activity_main);
        ARouter.getInstance().inject(this);

        findView();
    }

    private void findView() {
        viewPager = findViewById(R.id.talent_main_vp);
        fragmentManager = getSupportFragmentManager();
        fragment = new TalentMainFragment();
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
