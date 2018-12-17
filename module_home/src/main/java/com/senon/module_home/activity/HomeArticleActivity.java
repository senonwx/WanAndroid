package com.senon.module_home.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.adapter.RecycleHolder;
import com.senon.lib_common.adapter.RecyclerAdapter;
import com.senon.lib_common.base.BaseActivity;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.utils.StatusBarUtils;
import com.senon.module_home.R;
import com.senon.module_home.contract.HomeArticleActivityCon;
import com.senon.module_home.presenter.HomeArticleActivityPre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 最新博文列表页
 */

@Route(path = ConstantArouter.PATH_HOME_HOMEARTICLEACTIVITY)
public class HomeArticleActivity extends BaseActivity<HomeArticleActivityCon.View,HomeArticleActivityCon.Presenter> implements
        HomeArticleActivityCon.View {

    private LRecyclerView lrv;
    private boolean isLoadMore = false;//是否加载更多
    private boolean isDownRefesh = false;//是否下拉刷新
    private int currentPage = 0;//当前页数
    private RecyclerAdapter<HomeArticle.DatasBean> adapter;
    private HomeArticle articles;
    private ArrayList<HomeArticle.DatasBean> mData = new ArrayList<>();//原始数据
    private ArrayList<HomeArticle.DatasBean> tempData = new ArrayList<>();//间接数据
    private LRecyclerViewAdapter mLRecyclerViewAdapter;//Lrecyclerview的包装适配器

    @Override
    public int getLayoutId() {
        StatusBarUtils.with(this).init();
        return R.layout.home_activity_home_article;
    }
    @Override
    public HomeArticleActivityCon.Presenter createPresenter() {
        return new HomeArticleActivityPre(this);
    }
    @Override
    public HomeArticleActivityCon.View createView() {
        return this;
    }

    @Override
    public void init() {
        ComUtil.changeStatusBarTextColor(this,true);

        lrv = findViewById(R.id.lrv);
        ((TextView)findViewById(R.id.toolbar_title_tv)).setText("最新博文");
        findViewById(R.id.toolbar_back_igv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initLRecyclerVeiw();
    }

    private void initLRecyclerVeiw() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lrv.setLayoutManager(manager);
        lrv.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader); //设置下拉刷新Progress的样式
        lrv.setArrowImageView(R.mipmap.news_renovate);  //设置下拉刷新箭头
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        adapter = new RecyclerAdapter<HomeArticle.DatasBean>(this, mData,R.layout.home_adapter_homemain_fragment_article) {
            @Override
            public void convert(final RecycleHolder helper, final HomeArticle.DatasBean data, final int position) {
                helper.setText(R.id.type_tv,data.getSuperChapterName() + "/" + data.getChapterName());
                helper.setText(R.id.content_tv,data.getTitle());
                helper.setText(R.id.user_tv,data.getAuthor());
                helper.setText(R.id.time_tv,data.getNiceDate());
                helper.setVisible(R.id.new_tv,data.isFresh());
                helper.setVisible(R.id.top_layout,false);
            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lrv.setAdapter(mLRecyclerViewAdapter);
        //设置底部加载颜色
        lrv.setFooterViewColor(R.color.color_blue, R.color.text_gray, R.color.elegant_bg);
        lrv.setHeaderViewColor(R.color.color_blue, R.color.text_gray, R.color.elegant_bg);
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstPageData();
            }
        });
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
                currentPage++;
                getOrderList();
            }
        });

        lrv.forceToRefresh();
    }


    private void getFirstPageData() {
        isDownRefesh = true;
        currentPage = 0;
        getOrderList();
    }

    private void getOrderList() {
        getPresenter().getData(currentPage,false,true);
    }

    private void refreshData() {
        if (lrv == null) {
            return;
        }
        lrv.refreshComplete(currentPage);
        mLRecyclerViewAdapter.notifyDataSetChanged();
        isDownRefesh = false;
        isLoadMore = false;
    }
    
    @Override
    public void getDataResult(BaseResponse<HomeArticle> data) {
        articles = data.getData();

        tempData.clear();
        tempData.addAll(data.getData().getDatas());
        if (tempData.size() == 0 && mData.size() > 0 && isLoadMore) {//最后一页时
            lrv.setNoMore(true);
            isLoadMore = false;
        } else if (isDownRefesh) {//下拉刷新时
            mData.clear();
            mData.addAll(tempData);
            refreshData();
        } else {//加载更多时
            mData.addAll(tempData);
            refreshData();
        }
    }
}
