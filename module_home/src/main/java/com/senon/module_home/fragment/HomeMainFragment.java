package com.senon.module_home.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.net.bean.BaseResponse;
import com.senon.lib_common.utils.LogUtils;
import com.senon.module_home.R;
import com.senon.module_home.contract.HomeMainFragmentCon;
import com.senon.module_home.presenter.HomeMainFragmentPre;

/**
 * home mian fragment
 */
public class HomeMainFragment extends BaseLazyFragment<HomeMainFragmentCon.View, HomeMainFragmentCon.Presenter> implements
        HomeMainFragmentCon.View {

    private TextView title_tv;

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_main;
    }
    @Override
    public HomeMainFragmentCon.Presenter createPresenter() {
        return new HomeMainFragmentPre(mContext);
    }
    @Override
    public HomeMainFragmentCon.View createView() {
        return this;
    }
    @Override
    public void init(View rootView) {
        title_tv = rootView.findViewById(R.id.title_tv);
    }

    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //第一次可见时，自动加载页面
        LogUtils.e("-----> 子fragment进行初始化操作");

        title_tv.setText("首页");
    }

    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        //之后每次可见的操作
        LogUtils.e("-----> 子fragment每次可见时的操作");

    }

    @Override
    public void result(BaseResponse data) {

    }


}
