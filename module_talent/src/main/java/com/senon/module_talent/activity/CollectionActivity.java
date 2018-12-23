package com.senon.module_talent.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.adapter.RecycleHolder;
import com.senon.lib_common.adapter.RecyclerAdapter;
import com.senon.lib_common.base.BaseActivity;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.CollectionArticle;
import com.senon.lib_common.utils.BaseEvent;
import com.senon.lib_common.utils.StatusBarUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_talent.R;
import com.senon.module_talent.contract.CollectionActivityCon;
import com.senon.module_talent.presenter.CollectionActivityPre;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;

/**
 * 我的收藏列表页
 */
@Route(path = ConstantArouter.PATH_TALENT_COLLECTIONACTIVITY)
public class CollectionActivity extends BaseActivity<CollectionActivityCon.View,CollectionActivityCon.Presenter> implements
        CollectionActivityCon.View {


    private LRecyclerView lrv;
    private TextView toolbar_title_tv;
    private boolean isLoadMore = false;//是否加载更多
    private boolean isDownRefesh = false;//是否下拉刷新
    private int currentPage = 0;//当前页数
    private RecyclerAdapter<CollectionArticle.DatasBean> adapter;
    private CollectionArticle article;
    private ArrayList<CollectionArticle.DatasBean> mData = new ArrayList<>();//原始数据
    private ArrayList<CollectionArticle.DatasBean> tempData = new ArrayList<>();//间接数据
    private LRecyclerViewAdapter mLRecyclerViewAdapter;//Lrecyclerview的包装适配器

    @Override
    public int getLayoutId() {
        StatusBarUtils.with(this).init();
        return R.layout.talent_activity_collection;
    }
    @Override
    public CollectionActivityCon.Presenter createPresenter() {
        return new CollectionActivityPre(this);
    }
    @Override
    public CollectionActivityCon.View createView() {
        return this;
    }

    @Override
    public void init() {
        ComUtil.changeStatusBarTextColor(this,true);
        EventBus.getDefault().register(this);

        lrv = findViewById(R.id.lrv);
        toolbar_title_tv = ((TextView)findViewById(R.id.toolbar_title_tv));
        toolbar_title_tv.setText("我的收藏");
        toolbar_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lrv.smoothScrollToPosition(0);
            }
        });
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
        adapter = new RecyclerAdapter<CollectionArticle.DatasBean>(this, mData,R.layout.talent_adapter_collection) {
            @Override
            public void convert(final RecycleHolder helper, final CollectionArticle.DatasBean data, final int position) {
                helper.setText(R.id.type_tv,data.getChapterName());
                helper.setText(R.id.content_tv, Html.fromHtml(data.getTitle()).toString());
                helper.setText(R.id.user_tv,data.getAuthor());
                helper.setText(R.id.time_tv,data.getNiceDate());
                helper.setText(R.id.collection_tv,"已收藏");

                helper.setOnClickListener(R.id.content_lay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(ConstantArouter.PATH_COMMON_WEBVIEWCTIVITY)
                                .withInt("id",data.getOriginId())
                                .withString("url",data.getLink())
                                .withString("title",data.getTitle())
                                .withBoolean("isCollection",true)
                                .navigation();
                    }
                });
                helper.setOnClickListener(R.id.collection_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int originId = data.getOriginId() == 0 ? -1 : data.getOriginId();
                        getPresenter().getUnollect(data.getId(),originId,false,true);
                    }
                });
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
    public void getDataResult(BaseResponse<CollectionArticle> data) {
        if(data.getCode() == 0){
            article = data.getData();

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
        }else{
            ToastUtil.initToast(data.getMsg());
            refreshData();
            ARouter.getInstance().build(ConstantLoginArouter.PATH_COMMON_LOGINACTIVITY)
                    .navigation();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEventReceived(BaseEvent event) {
        if (event.getCode() == 101 && !event.isIngored()) {
            lrv.smoothScrollToPosition(0);
            lrv.forceToRefresh();
        }else if(event.getCode() == 0){
            lrv.forceToRefresh();
        }
    }

    @Override
    public void getCollectResult(int id, boolean isCollect) {
        BaseEvent event = new BaseEvent();
        event.setCode(101);
        event.setId(id);
        event.setCollect(isCollect);
        event.setIngored(true);
        EventBus.getDefault().post(event);

        for (int i = 0; i < mData.size(); i++) {
            if(id == mData.get(i).getId()){
                mData.remove(i);
                mLRecyclerViewAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
