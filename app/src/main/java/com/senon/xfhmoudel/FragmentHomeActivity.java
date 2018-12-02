package com.senon.xfhmoudel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.senon.lib_common.ConstantArouter;
import com.senon.module_home.fragment.HomeMainFragment;
import com.senon.module_life.fragment.LifeMainFragment;
import com.senon.module_talent.fragment.TalentMainFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ConstantArouter.PATH_APP_FRAGMENTHOMEACTIVITY)
public class FragmentHomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentManager fragmentManager;//声明fragment管理
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);
        viewPager = findViewById(R.id.vp);

        fragmentList.add(new HomeMainFragment());
        fragmentList.add(new LifeMainFragment());
        fragmentList.add(new TalentMainFragment());

        fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyFragmentPagerAdapter(fragmentManager));
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

    //FragmentPagerAdapter
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}

