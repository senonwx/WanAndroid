package com.senon.module_home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jerei.im.ApiConfig;
import com.pili.pldroid.player.AVOptions;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.widget.AutoCardView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;
import youth.elegant.zlzj.elegant_youth.R;
import youth.elegant.zlzj.elegant_youth.activity.MainActivity;
import youth.elegant.zlzj.elegant_youth.activity.group.GroupMainActivity;
import youth.elegant.zlzj.elegant_youth.activity.group.GroupMainIntroduceActivity;
import youth.elegant.zlzj.elegant_youth.activity.home.HomeCommonTitleWebActivity;
import youth.elegant.zlzj.elegant_youth.activity.home.HomeCommonWebActivity;
import youth.elegant.zlzj.elegant_youth.activity.home.OriginalMoreActivity;
import youth.elegant.zlzj.elegant_youth.activity.live.LivePlayActivity;
import youth.elegant.zlzj.elegant_youth.activity.live.LivePlaybackListActivity;
import youth.elegant.zlzj.elegant_youth.activity.talent.TalentWebviewActivity;
import youth.elegant.zlzj.elegant_youth.activity.user.HelpWebActivity;
import youth.elegant.zlzj.elegant_youth.activity.user.UserRecommendedActivity;
import youth.elegant.zlzj.elegant_youth.entity.HomeLive;
import youth.elegant.zlzj.elegant_youth.entity.HomeQuanZi;
import youth.elegant.zlzj.elegant_youth.entity.HomeXiuYou;
import youth.elegant.zlzj.elegant_youth.entity.TalentList;
import youth.elegant.zlzj.elegant_youth.utils.AppShareUtil;
import youth.elegant.zlzj.elegant_youth.utils.AutoTextView;
import youth.elegant.zlzj.elegant_youth.utils.ComUtil;
import youth.elegant.zlzj.elegant_youth.utils.GlideCircleTransform;
import youth.elegant.zlzj.elegant_youth.utils.SharedPerUtil;


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
    private List<HomeArticle> articles = new ArrayList<>();//最新博文
    private List<ProjectArticle> liveDatas = new ArrayList<>();//最新项目


    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    //设置banner图
    public void setBannerDatas(List<Banner> datas) {
        if (datas != null) {
            this.banners.clear();
            this.banners.addAll(datas);
            notifyDataChanged();
        }
    }

    //设置最新博文
    public void setArticleDatas(ArrayList<HomeArticle> datas){
        if(datas != null){
            this.articles.clear();
            this.articles.addAll(datas);
            notifyDataChanged();
        }
    }

    //设置最新项目
    public void setProjectDatas(List<ProjectArticle> datas) {
        if (datas != null) {
            this.liveDatas.clear();
            this.liveDatas.addAll(datas);
            notifyDataChanged();
        }
    }

    public HomeMainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_HEAD) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_main_head, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_ONE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_main_1, parent, false);
            return new OneViewHolder(view);
        } else if (viewType == ITEM_TWO) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_main_1, parent, false);
            return new TwoViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int mPosition) {
        if (holder instanceof HeaderViewHolder) {
            if(flag1){
                flag1 = false;
                ((HeaderViewHolder)holder).lin_lay.removeAllViews();

                //推荐好友的点击
                for (int i = 0; i < recommandDatas.size(); i++) {
                    ((HeaderViewHolder) holder).inflater = LayoutInflater.from(mContext).inflate(R.layout.item_home_main_item,null);
                    ImageView img =  ((HeaderViewHolder) holder).inflater.findViewById(R.id.img);
                    TextView title_tv =  ((HeaderViewHolder) holder).inflater.findViewById(R.id.title_tv);
                    TextView empty_tv =  ((HeaderViewHolder) holder).inflater.findViewById(R.id.empty_tv);

                    empty_tv.setVisibility(i == 0 ? View.VISIBLE:View.GONE);

                    Glide.with(mContext).load(recommandDatas.get(i).getImg_url()).into(img);
                    title_tv.setText(recommandDatas.get(i).getName());
                    if(i == 0){
                        title_tv.setVisibility(View.VISIBLE);
                    }
                    ((HeaderViewHolder) holder).lin_lay.addView(((HeaderViewHolder) holder).inflater);

                    final int finalI = i;
                    ((HeaderViewHolder) holder).inflater.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ComUtil.gotoUserPersonAct(mContext, recommandDatas.get(finalI).getJump_url());
                        }
                    });
                }
            }

            if(flag2){
                flag2 = false;
                ((HeaderViewHolder)holder).group_lay.removeAllViews();

                //热门圈子
                for (int i = 0; i < quziDatas.size() ; i++) {
                    ((HeaderViewHolder) holder).inflater2 = LayoutInflater.from(mContext).inflate(R.layout.item_home_main_item_group,null);
                    ImageView img =  ((HeaderViewHolder) holder).inflater2.findViewById(R.id.img);
                    TextView title_tv =  ((HeaderViewHolder) holder).inflater2.findViewById(R.id.title_tv);
                    TextView empty_tv =  ((HeaderViewHolder) holder).inflater2.findViewById(R.id.empty_tv);

                    empty_tv.setVisibility(i == 0 ? View.VISIBLE:View.GONE);

                    Glide.with(mContext).load(quziDatas.get(i).getUrl()).into(img);
                    title_tv.setText(quziDatas.get(i).getTitle());
                    if(i == 0){
                        title_tv.setVisibility(View.VISIBLE);
                    }
                    ((HeaderViewHolder) holder).group_lay.addView(((HeaderViewHolder) holder).inflater2);

                    final int finalI = i;
                    ((HeaderViewHolder) holder).inflater2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(quziDatas.get(finalI).getIs_join()){
                                mContext.startActivity(new Intent(mContext, GroupMainActivity.class).
                                        putExtra("circle_id",quziDatas.get(finalI).getCircle_id()));
                            }else {
                                mContext.startActivity(new Intent(mContext, GroupMainIntroduceActivity.class).
                                        putExtra("circle_id", quziDatas.get(finalI).getCircle_id()));
                            }
                        }
                    });
                }
            }
            //秀友总数
            ((HeaderViewHolder) holder).people_count.setText(peopleCount == 0 ? "在这里可以遇见很多秀友哦" : "在这里可以遇见"+peopleCount+"位秀友");

            //精彩直播
            for (int i = 0; i < liveDatas.size() ; i++) {
                Glide.with(mContext).load(liveDatas.get(0).getImg()).into(((HeaderViewHolder) holder).live_1_img);
                Glide.with(mContext).load(liveDatas.get(1).getImg()).into(((HeaderViewHolder) holder).live_2_img);
                ((HeaderViewHolder) holder).live_1_tv.setText(liveDatas.get(0).getRoom_name());
                ((HeaderViewHolder) holder).live_2_tv.setText(liveDatas.get(1).getRoom_name());
                ((HeaderViewHolder) holder).live_1_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intentlive(0);
                    }
                });
                ((HeaderViewHolder) holder).live_2_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intentlive(1);
                    }
                });
            }
            //首页banner图
            if(bannerImgs.size()!=0){
                ((HeaderViewHolder) holder).bgaBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                    @Override
                    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                        Glide.with(mContext)
                                .load(model)
                                .centerCrop()
                                .dontAnimate()
                                .into(itemView);
                    }
                });
                ((HeaderViewHolder) holder).bgaBanner.setData(bannerImgs, bannerStrs);
                ((HeaderViewHolder) holder).bgaBanner.setTransitionEffect(TransitionEffect.Alpha);
                ((HeaderViewHolder) holder).bgaBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                    @Override
                    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                        if(!jump_url.get(position).isEmpty()){
                            //如果jump_url是#，就不跳转
//                        if(jump_url.get(position).substring(0,1).equals("#")){
//                             return;
//                        }
                            if(position==0){
                                ((MainActivity)mContext).setCurrentFragmentPosition(2);
//                            mContext.startActivity(new Intent(mContext, HomeCommonWebActivity.class)
//                                    .putExtra("commonUrl",theSign));
                            }else if(position==2){
                                ((MainActivity)mContext).setCurrentFragmentPosition(4);
//                            BaseEvent event = new BaseEvent();
//                            event.setCode(36);
//                            event.setPosition(2);
//                            EventBus.getDefault().post(event);
                            }
                        }
                    }
                });
            }

            //互帮互助
            ((HeaderViewHolder) holder).first_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, HomeCommonTitleWebActivity.class)
                            .putExtra("commonUrl",theHelp)
                            .putExtra("commonTitle","互帮互助"));
                }
            });
            //邀请
            ((HeaderViewHolder) holder).second_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AppShareUtil().qrCodeDialog(mContext);
                }
            });
            //排行榜
            ((HeaderViewHolder) holder).third_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, HomeCommonWebActivity.class)
                            .putExtra("commonUrl",theRank));
                }
            });
            //签到
            ((HeaderViewHolder) holder).fourth_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, HomeCommonWebActivity.class)
                            .putExtra("commonUrl",theSign));
                }
            });

            //说说
            ((HeaderViewHolder) holder).fifth_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)mContext).setCurrentFragmentPosition(1);
                }
            });
            //美文
            ((HeaderViewHolder) holder).sixth_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)mContext).setCurrentFragmentPosition(3);
                }
            });
            //圈子
            ((HeaderViewHolder) holder).seventh_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)mContext).setCurrentFragmentPosition(4);
                }
            });
            //直播
            ((HeaderViewHolder) holder).eighth_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)mContext).setCurrentFragmentPosition(2);
                }
            });

            //两个专题活动点击  玩转
            ((HeaderViewHolder) holder).activity_igv_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,HelpWebActivity.class));
                }
            });
            //红花规则
            ((HeaderViewHolder) holder).activity_igv_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, HomeCommonTitleWebActivity.class)
                            .putExtra("commonUrl",theRule)
                            .putExtra("commonTitle","开宝箱规则"));
                }
            });
            //两个more点击
            ((HeaderViewHolder) holder).recommand_more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, UserRecommendedActivity.class));
                }
            });
            ((HeaderViewHolder) holder).show_more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, HomeCommonWebActivity.class)
                            .putExtra("commonUrl",showHeadLine));
                }
            });

            ((HeaderViewHolder) holder).auto_text_relay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, HomeCommonWebActivity.class)
                            .putExtra("commonUrl",showHeadLine));
                }
            });

            if(scrollViews.size()!=0){
                ((HeaderViewHolder) holder).mTextView.setText(scrollViews.get(0));
                //先销毁所有runnable 再启动计时器
                if(handler != null){
                    handler.removeCallbacksAndMessages(null);
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((HeaderViewHolder) holder).mTextView.next();
                        sCount++;
                        if(sCount>=Integer.MAX_VALUE){
                            sCount = scrollViews.size();
                        }
                        ((HeaderViewHolder) holder).mTextView.setText(scrollViews.get(sCount % (scrollViews.size())));
                        if (scrollViews.size()>1) {
                            handler.postDelayed(this, 2000);// 2000是延时时长
                        }
                    }
                }, 2000);
            }
        } else if (holder instanceof OneViewHolder) {
            final int position = mPosition - 1;
            final TalentList data = originalDatas.get(position);
            //注意除去头布局
            ((OneViewHolder) holder).itemView.setTag(position);

            ((OneViewHolder) holder).main_1_empty_tv.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            ((OneViewHolder) holder).original_title_lay.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            ((OneViewHolder) holder).igv_lay.setVisibility(View.GONE);

            ((OneViewHolder) holder).user_igv.setImageBitmap(null);
            ((OneViewHolder) holder).content_igv.setImageBitmap(null);
            Glide.with(mContext).load(data.getPortrait()).transform(new GlideCircleTransform(mContext)).into(((OneViewHolder) holder).user_igv);
            if(data.getImg_cover() != null){
                ((OneViewHolder) holder).igv_lay.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(data.getImg_cover()).into(((OneViewHolder) holder).content_igv);
            }

            ((OneViewHolder) holder).name_tv.setText(data.getNickname());
            ((OneViewHolder) holder).content_tv.setText(data.getSelect_describe());

            ((OneViewHolder) holder).original_more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, OriginalMoreActivity.class));
                }
            });
            ((OneViewHolder) holder).original_content_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, TalentWebviewActivity.class)
                            .putExtra("isRefresh",true)
                            .putExtra("id",data.getId())
                            .putExtra("position",0)
                            .putExtra("articleUid", data.getUid())
                            .putExtra("isReport",false));
                }
            });
        } else if (holder instanceof TwoViewHolder) {
//            final int position = mPosition - 1;
            //注意除去头布局

        }
    }

    @Override
    public int getItemCount() {
        return originalDatas.size() + headViewCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeadView(position)) {
            return ITEM_HEAD;
        }
//        else if(datas.get(position-1).isRelease()){
//            return ITEM_ONE;
//        }else if(!datas.get(position-1).isRelease()){
//            return ITEM_TWO;
//        }
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

    //头布局--
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout first_lay, second_lay, third_lay,fifth_lay,sixth_lay,seventh_lay;
        private RelativeLayout fourth_lay,eighth_lay;
        private BGABanner bgaBanner;
        private LinearLayout lin_lay;
        private View inflater,inflater2;
        private ImageView activity_igv_1,activity_igv_2;
        private TextView recommand_more_tv,show_more_tv;
        private AutoTextView mTextView;
        private AutoRelativeLayout auto_text_relay;

        private LinearLayout live_1_lay,live_2_lay,group_lay;
        private ImageView live_1_img,live_2_img;
        private TextView live_1_tv,live_2_tv;
        private TextView people_count;//用户个数

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTextView= itemView.findViewById(R.id.mTextView);
            first_lay = itemView.findViewById(R.id.first_lay);
            second_lay = itemView.findViewById(R.id.second_lay);
            third_lay = itemView.findViewById(R.id.third_lay);
            fourth_lay = itemView.findViewById(R.id.fourth_lay);
            fifth_lay = itemView.findViewById(R.id.fifth_lay);
            sixth_lay = itemView.findViewById(R.id.sixth_lay);
            seventh_lay = itemView.findViewById(R.id.seventh_lay);
            eighth_lay = itemView.findViewById(R.id.eighth_lay);
            activity_igv_1 = itemView.findViewById(R.id.activity_igv_1);
            activity_igv_2 = itemView.findViewById(R.id.activity_igv_2);
            recommand_more_tv = itemView.findViewById(R.id.recommand_more_tv);
            show_more_tv = itemView.findViewById(R.id.show_more_tv);
            auto_text_relay = itemView.findViewById(R.id.auto_text_relay);
            people_count = itemView.findViewById(R.id.head_count_tv);
            bgaBanner = itemView.findViewById(R.id.banner_guide_content);

            lin_lay = itemView.findViewById(R.id.lin_lay);
            lin_lay.removeAllViews();

            live_1_lay= itemView.findViewById(R.id.live_1_lay);
            live_2_lay = itemView.findViewById(R.id.live_2_lay);
            live_1_img = itemView.findViewById(R.id.live_1_img);
            live_2_img = itemView.findViewById(R.id.live_2_img);
            live_1_tv = itemView.findViewById(R.id.live_1_tv);
            live_2_tv = itemView.findViewById(R.id.live_2_tv);

            group_lay = itemView.findViewById(R.id.group_lay);
            group_lay.removeAllViews();

        }
    }


    //OneHolder
    class OneViewHolder extends RecyclerView.ViewHolder {
        private ImageView user_igv,content_igv;
        private AutoCardView igv_lay;
        private TextView name_tv, content_tv, original_more_tv,main_1_empty_tv;
        private AutoRelativeLayout original_content_lay,original_title_lay;

        public OneViewHolder(View itemView) {
            super(itemView);
            user_igv = itemView.findViewById(R.id.user_igv);
            content_igv = itemView.findViewById(R.id.content_igv);
            igv_lay = itemView.findViewById(R.id.igv_lay);

            name_tv = itemView.findViewById(R.id.name_tv);
            original_more_tv = itemView.findViewById(R.id.original_more_tv);
            content_tv = itemView.findViewById(R.id.content_tv);
            main_1_empty_tv = itemView.findViewById(R.id.main_1_empty_tv);

            original_content_lay = itemView.findViewById(R.id.original_content_lay);
            original_title_lay = itemView.findViewById(R.id.original_title_lay);
        }
    }

    //转发Holder
    class TwoViewHolder extends RecyclerView.ViewHolder {

        public TwoViewHolder(View itemView) {
            super(itemView);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onHeadItemClick(View view, int position);

        void onItemClick(View view, int position);
    }

    public void intentlive(int position){
        if(liveDatas.get(position).getIs_live()==1){
            Intent intent = new Intent(mContext, LivePlayActivity.class);
            intent.putExtra("room_num", liveDatas.get(position).getRoom_number());
            intent.putExtra("room_title", liveDatas.get(position).getRoom_name());
            intent.putExtra("zhubo_name", liveDatas.get(position).getNickname());
            intent.putExtra("live_bg", liveDatas.get(position).getImg());
            intent.putExtra("uid", liveDatas.get(position).getUid());
            intent.putExtra("like_count", liveDatas.get(position).getLike_count());
            intent.putExtra("liveStreaming", 1);//点播
            intent.putExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);//软解
            intent.putExtra("cache", false);//离线缓存
            mContext.startActivity(intent);
        }else {
            Intent intent = new Intent(mContext, LivePlaybackListActivity.class);
            intent.putExtra("title",liveDatas.get(position).getRoom_name())
                    .putExtra("portrait",liveDatas.get(position).getPortrait())
                    .putExtra("name",liveDatas.get(position).getNickname())
                    .putExtra("room_number",liveDatas.get(position).getRoom_number());
            mContext.startActivity(intent);
        }
    }

}

