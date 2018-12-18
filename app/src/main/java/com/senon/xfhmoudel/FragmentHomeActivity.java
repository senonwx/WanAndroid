package com.senon.xfhmoudel;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.utils.StatusBarUtils;
import com.senon.module_art.fragment.ArtMainFragment;
import com.senon.module_home.fragment.HomeMainFragment;
import com.senon.module_life.fragment.LifeMainFragment;
import com.senon.module_talent.fragment.TalentMainFragment;
import java.util.ArrayList;
import java.util.List;


@Route(path = ConstantArouter.PATH_APP_FRAGMENTHOMEACTIVITY)
public class FragmentHomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;
    private FragmentManager fragmentManager;//声明fragment管理
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.with(this).init();
        setContentView(R.layout.activity_fragment_home);
        ComUtil.changeStatusBarTextColor(this,true);

        viewPager = findViewById(R.id.vp);

        fragmentList.add(new HomeMainFragment());
        fragmentList.add(new LifeMainFragment());
        fragmentList.add(new ArtMainFragment());
        fragmentList.add(new TalentMainFragment());

        fragmentManager = getSupportFragmentManager();
        FragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());

        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabs.setTabsFromPagerAdapter(pagerAdapter);//给Tabs设置适配器
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }
}

