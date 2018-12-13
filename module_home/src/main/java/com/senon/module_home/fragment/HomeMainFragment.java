package com.senon.module_home.fragment;

import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.utils.LogUtils;
import com.senon.module_home.R;
import com.senon.module_home.contract.HomeMainFragmentCon;
import com.senon.module_home.presenter.HomeMainFragmentPre;

import java.util.List;

/**
 * home mian fragment
 */
public class HomeMainFragment extends BaseLazyFragment<HomeMainFragmentCon.View, HomeMainFragmentCon.Presenter> implements
        HomeMainFragmentCon.View {

    private LRecyclerView home_homefragment_lrv;
    private List<Banner> banners;
    private HomeArticle articles;
    private ProjectArticle projects;
    private int articlePage = 0;//首页文章页码
    private int projectPage = 1;//首页最新项目
    private int cid = 294;//项目id294

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
        home_homefragment_lrv = rootView.findViewById(R.id.home_homefragment_lrv);
    }

    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //第一次可见时，自动加载页面
        LogUtils.e("-----> 子fragment进行初始化操作");

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
    }

    @Override
    public void getHomeArticleResult(BaseResponse<HomeArticle> data) {
        articles = data.getData();
    }

    @Override
    public void getProjectListResult(BaseResponse<ProjectArticle> data) {
        projects = data.getData();
    }
}
