package com.senon.module_life.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.adapter.RecycleHolder;
import com.senon.lib_common.adapter.RecyclerAdapter;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.KnowledgeSystem;
import com.senon.lib_common.utils.LogUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_life.R;
import com.senon.module_life.contract.LifeMainFragmentCon;
import com.senon.module_life.presenter.LifeMainFragmentPre;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private LRecyclerView lrv;
    private TextView life_homefragment_title_tv;
    private List<KnowledgeSystem> knowledges = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;//Lrecyclerview的包装适配器
    private LinearLayoutManager layoutManager;
    private RecyclerAdapter<KnowledgeSystem> adapter;

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
        lrv = rootView.findViewById(R.id.life_homefragment_lrv);
        life_homefragment_title_tv = rootView.findViewById(R.id.life_homefragment_title_tv);
        life_homefragment_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lrv != null && adapter!= null){
                    lrv.smoothScrollToPosition(0);
                }
            }
        });
    }

    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //第一次可见时，自动加载页面
        LogUtils.e("-----> 子fragment进行初始化操作");

        //初始化adapter 设置适配器
        initAdapter();
        //添加滑动位置监听
        addLrvListener();
    }

    private void addLrvListener() {
        lrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //在此做处理
                if (null != layoutManager) {
                    //当前条目索引
                    int position = layoutManager.findFirstVisibleItemPosition();
                    if(position > 1){
                        life_homefragment_title_tv.setVisibility(View.VISIBLE);
                    }else if(position >= 0){
                        //根据view的高度来做显示隐藏判断
                        //根据索引来获取对应的itemView
                        View firstVisiableChildView = layoutManager.findViewByPosition(position);
                        //获取当前显示条目的高度
                        int itemHeight = firstVisiableChildView.getHeight();
                        //获取当前Recyclerview 偏移量
                        int offset = - firstVisiableChildView.getTop();
                        if (offset > itemHeight / 4) {
                            //做显示布局操作
                            life_homefragment_title_tv.setVisibility(View.VISIBLE);
                        } else {
                            //做隐藏布局操作
                            life_homefragment_title_tv.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });


    }

    private void getFirstData() {
        getPresenter().getKnowledgeList(false,true);
    }

    private void initAdapter() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lrv.setLayoutManager(layoutManager);
        lrv.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader); //设置下拉刷新Progress的样式
        lrv.setArrowImageView(R.mipmap.news_renovate);  //设置下拉刷新箭头
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        adapter = new RecyclerAdapter<KnowledgeSystem>(getContext(), knowledges, R.layout.life_adapter_lifemain_fragment) {
            @Override
            public void convert(final RecycleHolder helper, final KnowledgeSystem item, final int position) {
                helper.setVisible(R.id.textview,position == 0?true:false);
                helper.setVisible(R.id.home_placeholder_tv,position == knowledges.size() - 1?true:false);
                helper.setText(R.id.content_tv,item.getName());

                final TagFlowLayout flowLayout = helper.findView(R.id.flowlayout);
                flowLayout.setAdapter(new TagAdapter<KnowledgeSystem.ChildrenBean>(item.getChildren()){
                    @Override
                    public View getView(FlowLayout parent, int position, KnowledgeSystem.ChildrenBean bean){
                        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(
                                R.layout.life_adapter_lifemain_flowlayout_item, flowLayout, false);
                        tv.setText(item.getChildren().get(position).getName());
                        return tv;
                    }
                });
                flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent){
                        ARouter.getInstance().build(ConstantArouter.PATH_LIFE_KNOWLEDGESYSTEMACTIVITY)
                                .withString("title",item.getChildren().get(position).getName())
                                .withInt("cid",item.getChildren().get(position).getId())
                                .navigation();
                        return true;
                    }
                });

            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lrv.setAdapter(mLRecyclerViewAdapter);
        lrv.setLoadMoreEnabled(false);
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstData();
            }
        });
        lrv.forceToRefresh();
    }

    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        //之后每次可见的操作
        LogUtils.e("-----> 子fragment每次可见时的操作");

    }

    @Override
    public void getKnowledgeListResult(BaseResponse<List<KnowledgeSystem>> data) {
        knowledges.clear();
        knowledges.addAll(data.getData());

        mLRecyclerViewAdapter.notifyDataSetChanged();
        lrv.refreshComplete(0);
    }


}
