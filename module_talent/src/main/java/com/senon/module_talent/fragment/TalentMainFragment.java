package com.senon.module_talent.fragment;

import android.view.View;
import android.widget.TextView;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.utils.LogUtils;
import com.senon.module_talent.R;
import com.senon.module_talent.contract.TalentMainFragmentCon;
import com.senon.module_talent.presenter.TalentMainFragmentPre;

/**
 * talent mian fragment
 */
public class TalentMainFragment extends BaseLazyFragment<TalentMainFragmentCon.View, TalentMainFragmentCon.Presenter> implements
        TalentMainFragmentCon.View {

    private TextView title_tv;

    @Override
    public int getLayoutId() {
        return R.layout.talent_fragment_main;
    }
    @Override
    public TalentMainFragmentCon.Presenter createPresenter() {
        return new TalentMainFragmentPre(mContext);
    }
    @Override
    public TalentMainFragmentCon.View createView() {
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

        title_tv.setText("文采");
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
