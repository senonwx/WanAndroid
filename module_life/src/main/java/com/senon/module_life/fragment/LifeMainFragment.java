package com.senon.module_life.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.utils.LogUtils;
import com.senon.module_life.R;
import com.senon.module_life.contract.LifeMainFragmentCon;
import com.senon.module_life.presenter.LifeMainFragmentPre;

/**
 * 生命周期执行的方法 如下：
 * 第一次生成页面-->可见
 * setUserVisibleHint: ----->false
 * setUserVisibleHint: ----->true
 * onCreateView: -----> onCreateView
 * onStart: -----> onStart
 * onFragmentFirst: 首次可见
 * onFragmentFirst: -----> 子fragment进行初始化操作
 * onResume: -----> onResume
 *
 * 可见-->第一次隐藏：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 *
 * 未销毁且不可见-->重新可见：
 * onStart: -----> onStart
 * onFragmentVisble: 可见
 * onFragmentVisble: -----> 子fragment每次可见时的操作
 * onResume: -----> onResume
 *
 * 可见-->销毁：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 * onDestroyView: -----> onDestroyView
 *
 * 我们可以更具以上生命周期来操作不同的业务逻辑，
 * 请务必运行此module demo，观看打印日志来自定义逻辑。
 */
public class LifeMainFragment extends BaseLazyFragment<LifeMainFragmentCon.View, LifeMainFragmentCon.Presenter> implements
        LifeMainFragmentCon.View {

    private View node_v;
    private TextView title_tv;
    private ImageView head_igv,plus_tv,empty_igv;
    private LRecyclerView life_lrv;

    @Override
    public int getLayoutId() {
        return R.layout.life_fragment_main;
    }
    @Override
    public LifeMainFragmentCon.Presenter createPresenter() {
        return new LifeMainFragmentPre(mContext);
    }
    @Override
    public LifeMainFragmentCon.View createView() {
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

        title_tv.setText("生活");
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
