package com.senon.module_home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.utils.LogUtils;
import com.senon.module_home.R;
import com.senon.module_home.adapter.HomeMainAdapter;
import com.senon.module_home.contract.HomeMainFragmentCon;
import com.senon.module_home.presenter.HomeMainFragmentPre;

import java.util.ArrayList;
import java.util.List;

/**
 * home mian fragment
 */
public class HomeMainFragment extends BaseLazyFragment<HomeMainFragmentCon.View, HomeMainFragmentCon.Presenter> implements
        HomeMainFragmentCon.View {

    private LRecyclerView lrv;
    private TextView home_homefragment_title_tv;
    private List<Banner> banners = new ArrayList<>();
    private HomeArticle articles;
    private ProjectArticle projects;
    private final int articlePage = 0;//首页文章页码
    private final int projectPage = 1;//首页最新项目
    private HomeMainAdapter adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;//Lrecyclerview的包装适配器
    private LinearLayoutManager layoutManager;


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
        lrv = rootView.findViewById(R.id.home_homefragment_lrv);
        home_homefragment_title_tv = rootView.findViewById(R.id.home_homefragment_title_tv);
        home_homefragment_title_tv.setOnClickListener(new View.OnClickListener() {
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
        //请求网络数据
        getFirstData();
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
                        home_homefragment_title_tv.setVisibility(View.VISIBLE);
                    }else{
                        //根据view的高度来做显示隐藏判断
                        //根据索引来获取对应的itemView
                        View firstVisiableChildView = layoutManager.findViewByPosition(position);
                        //获取当前显示条目的高度
                        int itemHeight = firstVisiableChildView.getHeight();
                        //获取当前Recyclerview 偏移量
                        int offset = - firstVisiableChildView.getTop();
                        if (offset > itemHeight / 4) {
                            //做显示布局操作
                            home_homefragment_title_tv.setVisibility(View.VISIBLE);
                        } else {
                            //做隐藏布局操作
                            home_homefragment_title_tv.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    private void initAdapter() {
        adapter = new HomeMainAdapter(mContext);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        lrv.setLayoutManager(layoutManager);
        lrv.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader); //设置下拉刷新Progress的样式
        lrv.setArrowImageView(R.mipmap.news_renovate);  //设置下拉刷新箭头
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lrv.setAdapter(mLRecyclerViewAdapter);
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstData();
            }
        });
        lrv.setLoadMoreEnabled(false);
    }

    private void getFirstData(){
        //加载banner
        getPresenter().getBanner(false, true);
        //加载首页最新文章
        getPresenter().getHomeArticle(articlePage,false, true);
        //加载项目列表数据-完整项目模块
        getPresenter().getHomeProject(projectPage,false, true);
    }

    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        //之后每次可见的操作
        LogUtils.e("-----> 子fragment每次可见时的操作");
    }

    @Override
    public void getBannerResult(BaseResponse<List<Banner>> data) {
        banners = data.getData();
        adapter.setBannerDatas(banners);
        refreshData();

    }

    @Override
    public void getHomeArticleResult(BaseResponse<HomeArticle> data) {
        List<HomeArticle.DatasBean> list = new ArrayList<>();
        if(data.getData() != null){
            for (int i = 0; i < data.getData().getDatas().size(); i++) {
                if(i < 5){
                    list.add(data.getData().getDatas().get(i));
                }
            }
        }
        data.getData().setDatas(list);

        articles = data.getData();
        adapter.setArticleDatas(articles.getDatas());
        refreshData();

    }

    @Override
    public void getHomeProjectResult(BaseResponse<ProjectArticle> data) {
        List<ProjectArticle.DatasBean> list = new ArrayList<>();
        if(data.getData() != null){
            for (int i = 0; i < data.getData().getDatas().size(); i++) {
                if(i < 5){
                    list.add(data.getData().getDatas().get(i));
                }
            }
        }
        data.getData().setDatas(list);

        projects = data.getData();
        adapter.setProjectDatas(projects.getDatas());
        refreshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null){//banner生命周期需要调用的方法start
            adapter.setBannerStatus(1);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(adapter != null){//banner生命周期需要调用的方法pause
            adapter.setBannerStatus(2);
        }
    }

    //完成数据加载
    private void refreshData(){
        lrv.refreshComplete(0);
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }
}
