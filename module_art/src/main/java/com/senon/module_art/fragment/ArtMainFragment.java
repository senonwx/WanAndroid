package com.senon.module_art.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.base.BaseLazyFragment;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.bean.WXarticle;
import com.senon.lib_common.bean.WXchapters;
import com.senon.lib_common.utils.BaseEvent;
import com.senon.lib_common.utils.LogUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_art.R;
import com.senon.module_art.adapter.ArtMainAdapter;
import com.senon.module_art.contract.ArtMainFragmentCon;
import com.senon.module_art.presenter.ArtMainFragmentPre;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * art mian fragment
 */
public class ArtMainFragment extends BaseLazyFragment<ArtMainFragmentCon.View, ArtMainFragmentCon.Presenter> implements
        ArtMainFragmentCon.View {

    public final static int ID = -1;
    private final static int PAGE = 1;
    private LRecyclerView lrv;
    private TextView home_homefragment_title_tv;
    private List<WXchapters> chapters = new ArrayList<>();
    private WXarticle articles;
    private int page = PAGE;//公众号页码
    private int id = ID;//公众号id
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

        EventBus.getDefault().register(this);

        //初始化adapter 设置适配器
        initAdapter();
        //请求网络数据
        getWXarticleChapters();
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
        lrv.setArrowImageView(R.mipmap.news_renovate);  //设置下拉刷新箭头
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lrv.setAdapter(mLRecyclerViewAdapter);
        //设置底部加载颜色
        lrv.setFooterViewColor(R.color.color_blue, R.color.text_gray, R.color.elegant_bg);
        lrv.setHeaderViewColor(R.color.color_blue, R.color.text_gray, R.color.elegant_bg);
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWXarticleChapters();
            }
        });
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getWXarticleList(id,++page);
            }
        });
        adapter.setOnItemClickListener(new ArtMainAdapter.OnItemClickListener() {
            @Override
            public void onHeadItemClick(View view, int position,int mId) {
                id = mId;
                lrv.forceToRefresh();
            }
            @Override
            public void onItemClick(View view, int position) {
            }
        });

        lrv.forceToRefresh();
    }

    //获取公众号列表
    private void getWXarticleChapters(){
        getPresenter().getWXarticleChapters(false, true);
    }

    //查看某个公众号历史数据
    private void getWXarticleList(int id,int page){
        getPresenter().getWXarticleList(id,page,false, true);
    }

    //完成数据加载
    private void refreshData(){
        lrv.refreshComplete(page);
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }
    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        //之后每次可见的操作
        LogUtils.e("-----> 子fragment每次可见时的操作");

    }


    @Override
    public void getWXartChapResult(BaseResponse<List<WXchapters>> data) {
        if(data.getData() != null && !data.getData().isEmpty()){
            chapters.clear();
            chapters.addAll(data.getData());

            if(id == ID){
                id = chapters.get(0).getId();//初始id为第一个id
            }

            adapter.setChaptersDatas(chapters,id);

            page = PAGE;//初始化页码为第一页
            getWXarticleList(id,page);

        }

    }


    @Override
    public void getWXartListResult(BaseResponse<WXarticle> data) {
        if(data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()){
            if(page == PAGE){
                articles = data.getData();
            }else{
                articles.getDatas().addAll(data.getData().getDatas());
            }

            adapter.setArticleDatas(articles.getDatas());
        }

        refreshData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEventReceived(BaseEvent event) {
        if(event.getCode() == 1){//退出登录  刷新列表
            getWXarticleChapters();
        }else if (event.getCode() == 101) {
            int id = event.getId();
            boolean isCollect = event.isCollect();
            for (WXarticle.DatasBean bean : articles.getDatas()) {
                if(bean.getId() == id){
                    bean.setCollect(isCollect);

                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }
}
