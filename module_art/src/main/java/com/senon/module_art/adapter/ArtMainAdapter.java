package com.senon.module_art.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.senon.lib_common.bean.WXarticle;
import com.senon.lib_common.bean.WXchapters;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_art.R;
import java.util.ArrayList;
import java.util.List;


/**
 * art主界面--列表Recycleview适配器
 */
public class ArtMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private int ITEM_ONE = 2;
    private int ITEM_HEAD = 1;
    private int headViewCount = 1;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private List<WXchapters> chapters = new ArrayList<>();//公众号列表
    private List<WXarticle.DatasBean> articles = new ArrayList<>();//公众号文章

    public void notifyDataChanged() {
        notifyDataSetChanged();
    }


    //设置公众号文章
    public void setArticleDatas(List<WXarticle.DatasBean> datas) {
        if (datas != null) {
            this.articles.clear();
            this.articles.addAll(datas);
            notifyDataChanged();
        }
    }
    //设置公众号列表
    public void setChaptersDatas(List<WXchapters> datas) {
        if (datas != null) {
            this.chapters.clear();
            this.chapters.addAll(datas);
            notifyDataChanged();
        }
    }

    public ArtMainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_HEAD) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.art_adapter_artmain_fragment_head, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_ONE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.art_adapter_artmain_fragment_article, parent, false);
            return new OneViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int mPosition) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof OneViewHolder) {
            //注意除去头布局
            final int position = mPosition - 1;
            final WXarticle.DatasBean data = articles.get(position);
            ((OneViewHolder) holder).itemView.setTag(position);

            ((OneViewHolder) holder).type_tv.setText(data.getSuperChapterName() + "/" + data.getChapterName());
            ((OneViewHolder) holder).content_tv.setText(Html.fromHtml(data.getTitle()));
            ((OneViewHolder) holder).user_tv.setText(data.getAuthor());
            ((OneViewHolder) holder).time_tv.setText(data.getNiceDate());
            ((OneViewHolder) holder).author_tv.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            ((OneViewHolder) holder).art_placeholder_tv.setVisibility(position == articles.size()-1 ? View.VISIBLE : View.GONE);
            ((OneViewHolder) holder).author_tv.setText(data.getAuthor());
            ((OneViewHolder) holder).like_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articles.size() + headViewCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeadView(position)) {
            return ITEM_HEAD;
        }
        return ITEM_ONE;
    }

    public boolean isHeadView(int position) {
        return headViewCount != 0 && position < headViewCount;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    //公众号列表
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView lrv;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            lrv = itemView.findViewById(R.id.art_main_fragment_head_lrv);
        }
    }

    //最新博文
    class OneViewHolder extends RecyclerView.ViewHolder {
        private TextView author_tv;
        private TextView type_tv, content_tv, user_tv, time_tv;
        private TextView like_tv;
        private View art_placeholder_tv;

        public OneViewHolder(View itemView) {
            super(itemView);
            author_tv = itemView.findViewById(R.id.author_tv);
            type_tv = itemView.findViewById(R.id.type_tv);
            content_tv = itemView.findViewById(R.id.content_tv);
            user_tv = itemView.findViewById(R.id.user_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            like_tv = itemView.findViewById(R.id.like_tv);
            art_placeholder_tv = itemView.findViewById(R.id.art_placeholder_tv);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onHeadItemClick(View view, int position);
        void onItemClick(View view, int position);
    }



}

