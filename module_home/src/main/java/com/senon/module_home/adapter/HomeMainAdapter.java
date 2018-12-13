package com.senon.module_home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_home.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import java.util.ArrayList;
import java.util.List;


/**
 * Home主界面--列表Recycleview适配器
 */
public class HomeMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private int ITEM_ONE = 1;
    private int ITEM_TWO = 2;
    private int ITEM_HEAD = 3;
    private int headViewCount = 1;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private List<Banner> banners = new ArrayList<>();//banner图
    private List<HomeArticle.DatasBean> articles = new ArrayList<>();//最新博文
    private List<ProjectArticle.DatasBean> projects = new ArrayList<>();//最新项目
    private int bannerRefresh = 0;//0：设置banner     1：设置了 并且start     2：设置了 并且pause

    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    //设置banner图
    public void setBannerDatas(List<Banner> datas) {
        if (datas != null) {
            this.banners.clear();
            this.banners.addAll(datas);
            bannerRefresh = 0;
            notifyDataChanged();
        }
    }
    public void setBannerStatus(int bannerRefresh){
        this.bannerRefresh = bannerRefresh;
        notifyDataChanged();
    }

    //设置最新博文
    public void setArticleDatas(List<HomeArticle.DatasBean> datas) {
        if (datas != null) {
            this.articles.clear();
            this.articles.addAll(datas);
            notifyDataChanged();
        }
    }

    //设置最新项目
    public void setProjectDatas(List<ProjectArticle.DatasBean> datas) {
        if (datas != null) {
            this.projects.clear();
            this.projects.addAll(datas);
            notifyDataChanged();
        }
    }

    public HomeMainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_HEAD) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_adapter_homemain_fragment_head, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_ONE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_adapter_homemain_fragment_article, parent, false);
            return new OneViewHolder(view);
        } else if (viewType == ITEM_TWO) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_adapter_homemain_fragment_project, parent, false);
            return new TwoViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int mPosition) {
        if (holder instanceof HeaderViewHolder) {
            if(bannerRefresh == 0){
                bannerRefresh = -1;
                // 设置页面点击事件
                ((HeaderViewHolder) holder).banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
                    @Override
                    public void onPageClick(View view, int position) {
                        ToastUtil.initToast("click page:"+position);
                    }
                });
                // 设置数据
                ((HeaderViewHolder) holder).banner.setPages(banners, new MZHolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new BannerViewHolder();
                    }
                });
                ((HeaderViewHolder) holder).banner.start();
            }else if(bannerRefresh == 1){
                bannerRefresh = -1;
                ((HeaderViewHolder) holder).banner.start();
            }else if(bannerRefresh == 2){
                bannerRefresh = -1;
                ((HeaderViewHolder) holder).banner.pause();
            }
        } else if (holder instanceof OneViewHolder) {
            //注意除去头布局
            final int position = mPosition - 1;
            final HomeArticle.DatasBean data = articles.get(position);
            ((OneViewHolder) holder).itemView.setTag(position);

            ((OneViewHolder) holder).type_tv.setText(data.getSuperChapterName() + "/" + data.getChapterName());
            ((OneViewHolder) holder).content_tv.setText(data.getTitle());
            ((OneViewHolder) holder).user_tv.setText(data.getAuthor());
            ((OneViewHolder) holder).time_tv.setText(data.getNiceDate());
            ((OneViewHolder) holder).new_tv.setVisibility(data.isFresh() ? View.VISIBLE : View.GONE);
            ((OneViewHolder) holder).top_layout.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            ((OneViewHolder) holder).more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            ((OneViewHolder) holder).like_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        } else if (holder instanceof TwoViewHolder) {
            //注意除去头布局
            final int position = mPosition - 1 - articles.size();
            final ProjectArticle.DatasBean data = projects.get(position);
            ((TwoViewHolder) holder).itemView.setTag(position);

            ((TwoViewHolder) holder).content_igv.setImageBitmap(null);
            Glide.with(mContext).load(data.getEnvelopePic()).into(((TwoViewHolder) holder).content_igv);
            ((TwoViewHolder) holder).content_tv.setText(data.getDesc());
            ((TwoViewHolder) holder).title_tv.setText(data.getTitle());
            ((TwoViewHolder) holder).user_tv.setText(data.getAuthor());
            ((TwoViewHolder) holder).time_tv.setText(data.getNiceDate());
            ((TwoViewHolder) holder).top_layout.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            ((TwoViewHolder) holder).more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return articles.size() + projects.size() + headViewCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeadView(position)) {
            return ITEM_HEAD;
        } else if (isItemOne(position)) {
            return ITEM_ONE;
        } else if (isItemTwo(position)) {
            return ITEM_TWO;
        }
        return 0;
    }

    public boolean isHeadView(int position) {
        return headViewCount != 0 && position < headViewCount;
    }

    public boolean isItemOne(int position) {
        if (articles.size() == 0) {
            return false;
        } else if (position <= articles.size()) {
            return true;
        }
        return false;
    }

    public boolean isItemTwo(int position) {
        if (projects.size() == 0) {
            return false;
        } else if (position <= articles.size() + projects.size()) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    //头布局--轮播图
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private MZBannerView banner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);

        }
    }

    //最新博文
    class OneViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout top_layout;
        private TextView more_tv, type_tv, content_tv, user_tv, time_tv;
        private TextView new_tv, like_tv;

        public OneViewHolder(View itemView) {
            super(itemView);
            top_layout = itemView.findViewById(R.id.top_layout);
            more_tv = itemView.findViewById(R.id.more_tv);
            type_tv = itemView.findViewById(R.id.type_tv);
            content_tv = itemView.findViewById(R.id.content_tv);
            user_tv = itemView.findViewById(R.id.user_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            new_tv = itemView.findViewById(R.id.new_tv);
            like_tv = itemView.findViewById(R.id.like_tv);
        }
    }

    //最新项目
    class TwoViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout top_layout;
        private ImageView content_igv;
        private TextView more_tv, title_tv, content_tv, user_tv, time_tv;
        private TextView like_tv;

        public TwoViewHolder(View itemView) {
            super(itemView);
            top_layout = itemView.findViewById(R.id.top_layout);
            content_igv = itemView.findViewById(R.id.content_igv);
            more_tv = itemView.findViewById(R.id.more_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            content_tv = itemView.findViewById(R.id.content_tv);
            user_tv = itemView.findViewById(R.id.user_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
//            like_tv = itemView.findViewById(R.id.like_tv);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onHeadItemClick(View view, int position);

        void onItemClick(View view, int position);
    }

    public static class BannerViewHolder implements MZViewHolder<Banner> {
        private ImageView igv;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.home_adapter_homemain_fragment_banneritem,null);
            igv = view.findViewById(R.id.igv);
            return view;
        }
        @Override
        public void onBind(Context context, int i, Banner banner) {
            // 数据绑定
            Glide.with(context).load(banner.getImagePath()).into(igv);
        }

    }


}

