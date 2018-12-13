package com.senon.module_home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

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
    private List<Banner> banners;
    private HomeArticle articles;
    private ProjectArticle projects;
    private int articlePage = 0;//首页文章页码
    private int projectPage = 1;//首页最新项目
    private int cid = 294;//项目id294
    private HomeMainAdapter adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;//Lrecyclerview的包装适配器


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
    }

    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //第一次可见时，自动加载页面
        LogUtils.e("-----> 子fragment进行初始化操作");

        adapter = new HomeMainAdapter(mContext);
        lrv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        lrv.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader); //设置下拉刷新Progress的样式
        lrv.setArrowImageView(R.mipmap.news_renovate);  //设置下拉刷新箭头
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lrv.setAdapter(mLRecyclerViewAdapter);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setFooterViewColor(R.color.color_blue, R.color.text_gray, R.color.elegant_bg);
        lrv.setHeaderViewColor(R.color.common_white, R.color.common_white, R.color.tablayout_tv);
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstData();
            }
        });
        lrv.setLoadMoreEnabled(false);

        getFirstData();

    }

    private void getFirstData(){
        //加载banner
        getPresenter().getBanner(true, true);
        //加载首页最新文章
        getPresenter().getHomeArticle(articlePage,true, true);
        //加载项目列表数据-完整项目模块
        getPresenter().getProjectList(projectPage,cid,true, true);
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
    }

    @Override
    public void getProjectListResult(BaseResponse<ProjectArticle> data) {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.setBannerStatus(1);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(adapter != null){
            adapter.setBannerStatus(2);
        }
    }
}
