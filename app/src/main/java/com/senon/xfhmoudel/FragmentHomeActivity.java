package com.senon.xfhmoudel;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.utils.StatusBarUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_art.fragment.ArtMainFragment;
import com.senon.module_home.fragment.HomeMainFragment;
import com.senon.module_life.fragment.LifeMainFragment;
import com.senon.module_talent.fragment.TalentMainFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * App主页
 */
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
        tabs = findViewById(R.id.tabs);

        initVp$Tab();
    }

    private void initVp$Tab() {
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

        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
                View view = tab.getCustomView();
                if(i == 0){
                    setTextColor(view,0,true);
                }
            }
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在这里可以设置选中状态下  tab字体显示样式
                View view = tab.getCustomView();
                setTextColor(view,tab.getPosition(),true);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                setTextColor(view,tab.getPosition(),false);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private String[] titles = {"首页","体系","公众号","我的"};
    private int[] colors = {R.color.login_bg_end_1,R.color.tablayout_tv_gray};
    private int[] mipmaps = {R.mipmap.ic_tabbar_discover,R.mipmap.ic_tabbar_mainframe,R.mipmap.ic_tabbar_order,R.mipmap.ic_tabbar_me};
    private int[] mipmaphls = {R.mipmap.ic_tabbar_discoverhl,R.mipmap.ic_tabbar_mainframehl,R.mipmap.ic_tabbar_orderhl,R.mipmap.ic_tabbar_mehl};

    private View getTabView(int curPos) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_fragmhome_tab, null);
        ImageView igv = (ImageView) view.findViewById(R.id.tab_item_igv);
        TextView tv = (TextView) view.findViewById(R.id.tab_item_tv);
        tv.setText(titles[curPos]);
        igv.setImageResource(mipmaps[curPos]);

        setTextColor(tv,curPos,false);
        return view;
    }

    private void setTextColor(View view,int position,boolean select){
        if (null != view && view instanceof RelativeLayout) {
            TextView tv = (TextView) view.findViewById(R.id.tab_item_tv);
            ImageView igv = (ImageView) view.findViewById(R.id.tab_item_igv);

            tv.setTextSize(TypedValue.COMPLEX_UNIT_MM,22);
            tv.setTextColor(ContextCompat.getColor(this,select ? colors[0] : colors[1]));

            igv.setImageResource(select ? mipmaphls[position] : mipmaps[position]);
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    ToastUtil.initToast("再按一次返回键退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
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

