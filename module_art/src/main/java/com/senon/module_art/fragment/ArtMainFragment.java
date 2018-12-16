package com.senon.module_art.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.bean.WXarticle;
import com.senon.lib_common.bean.WXchapters;
import com.senon.lib_common.utils.LogUtils;
import com.senon.module_art.R;
import com.senon.module_art.adapter.ArtMainAdapter;
import com.senon.module_art.contract.ArtMainFragmentCon;
import com.senon.module_art.presenter.ArtMainFragmentPre;

import java.util.ArrayList;
import java.util.List;

/**
 * art mian fragment
 */
public class ArtMainFragment extends BaseLazyFragment<ArtMainFragmentCon.View, ArtMainFragmentCon.Presenter> implements
        ArtMainFragmentCon.View {

    private LRecyclerView lrv;
    private TextView home_homefragment_title_tv;
    private List<WXchapters> chapters = new ArrayList<>();
    private WXarticle articles;
    private int page = 1;//公众号页码
    private ArtMainAdapter adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;//Lrecyclerview的包装适配器
    private LinearLayoutManager layoutManager;


    @Override
    public int getLayoutId() {
        return R.layout.art_fragment_main;
    }
    @Override
    public ArtMainFragmentCon.Presenter createPresenter() {
        return new ArtMainFragmentPre(mContext);
    }
    @Override
    public ArtMainFragmentCon.View createView() {
        return this;
    }
    @Override
    public void init(View rootView) {
        lrv = rootView.findViewById(R.id.home_homefragment_lrv);
        home_homefragment_title_tv = rootView.findViewById(R.id.home_homefragment_title_tv);
    }

    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //第一次可见时，自动加载页面
        LogUtils.e("-----> 子fragment进行初始化操作");

        //初始化adapter 设置适配器
        initAdapter();
        //请求网络数据
//        getWXarticleChapters();
        getWXarticleList(408,1);
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
        adapter = new ArtMainAdapter(mContext);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        lrv.setLayoutManager(layoutManager);
        lrv.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader); //设置下拉刷新Progress的样式
        lrv.setArrowImageView(R.drawable.news_renovate);  //设置下拉刷新箭头
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lrv.setAdapter(mLRecyclerViewAdapter);
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWXarticleChapters();
            }
        });
        lrv.setLoadMoreEnabled(false);
    }

    private void getWXarticleChapters(){
        //获取公众号列表
        getPresenter().getWXarticleChapters(true, true);

    }

    private void getWXarticleList(int id,int page){
        //查看某个公众号历史数据
        getPresenter().getWXarticleList(id,page,true, true);
    }

    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        //之后每次可见的操作
        LogUtils.e("-----> 子fragment每次可见时的操作");
    }


    @Override
    public void getWXartChapResult(BaseResponse<List<WXchapters>> data) {
//        List<WXchapters> list = new ArrayList<>();
//        if(data.getData() != null){
//            for (int i = 0; i < data.getData().getDatas().size(); i++) {
//                if(i < 5){
//                    list.add(data.getData().getDatas().get(i));
//                }
//            }
//        }
//        data.getData().setDatas(list);
//
//        chapters = data.getData();
//        adapter.setProjectDatas(projects.getDatas());
    }

    @Override
    public void getWXartListResult(BaseResponse<WXarticle> data) {
//        List<HomeArticle.DatasBean> list = new ArrayList<>();
//        if(data.getData() != null){
//            for (int i = 0; i < data.getData().getDatas().size(); i++) {
//                if(i < 5){
//                    list.add(data.getData().getDatas().get(i));
//                }
//            }
//        }
//        data.getData().setDatas(list);
//
//        articles = data.getData();
//        adapter.setArticleDatas(articles.getDatas());
        adapter.setArticleDatas(data.getData().getDatas());
    }
}
