package com.senon.lib_common.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class RecycleHolder extends RecyclerView.ViewHolder {

    /**
     * 用于存储当前item当中的View
     */
    private SparseArray<View> mViews;

    public RecycleHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<View>();
    }

    public <T extends View> T findView(int ViewId) {
        View view = mViews.get(ViewId);
        //集合中没有，则从item当中获取，并存入集合当中
        if (view == null) {
            view = itemView.findViewById(ViewId);
            mViews.put(ViewId, view);
        }
        return (T) view;
    }

    public RecycleHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RecycleHolder setOnClickListener(int viewId,int viewId2 ,View.OnClickListener listener) {
        View view = findView(viewId);
        view.setOnClickListener(listener);

        View view2 = findView(viewId2);
        view2.setOnClickListener(listener);
        return this;
    }

    public RecycleHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = findView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public RecycleHolder setOnLongClickListener(int viewId,int viewId2, View.OnLongClickListener listener) {
        View view = findView(viewId);
        view.setOnLongClickListener(listener);

        View view2 = findView(viewId2);
        view2.setOnLongClickListener(listener);
        return this;
    }

    public RecycleHolder setText(int viewId, String text) {
        TextView tv = findView(viewId);
        tv.setText(text);
        return this;
    }

    public RecycleHolder setText(int viewId, SpannableStringBuilder text) {
        TextView tv = findView(viewId);
        tv.setText(text);
        return this;
    }

    public RecycleHolder setMaxLine(int viewId, int lineCount) {
        TextView tv = findView(viewId);
        tv.setMaxLines(lineCount);
        return this;
    }

    public RecycleHolder setPadding(int viewId, int left, int top, int right, int bottom) {
        View tv = findView(viewId);
        tv.setPadding(left, top, right, bottom);
        return this;
    }

    public RecycleHolder setAppendTextColor(int viewId, CharSequence str, int resColorId, int resDimen) {
        TextView tv = findView(viewId);
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(tv.getContext().getResources().getColor(resColorId)), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan((int) tv.getContext().getResources().getDimension(resDimen)), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spannableString);
        return this;
    }

    public RecycleHolder setSelected(int viewId, boolean selected) {
        View tv = findView(viewId);
        tv.setSelected(selected);
        return this;
    }

    public RecycleHolder setEnabled(int viewId, boolean enabled) {
        View tv = findView(viewId);
        tv.setEnabled(enabled);
        return this;
    }
    public RecycleHolder setEnabled_imgView(int viewId, boolean enabled) {
        ImageView tv = findView(viewId);
        tv.setEnabled(enabled);
        return this;
    }
    public RecycleHolder setText(int viewId, int text) {
        TextView tv = findView(viewId);
        tv.setText(text);
        return this;
    }

    public RecycleHolder setChecked(int viewId, boolean checked) {
        Checkable view = findView(viewId);
        view.setChecked(checked);
        return this;
    }

    public RecycleHolder setTextColor(int viewId, int colorResId) {
        TextView tv = findView(viewId);
        tv.setTextColor(tv.getContext().getResources().getColor(colorResId));
        return this;
    }

    public RecycleHolder setImageResource(int viewId, int ImageId) {
        ImageView image = findView(viewId);
        image.setImageResource(ImageId);
        return this;
    }

    public RecycleHolder setLayoutParams(int viewId, ViewGroup.LayoutParams para) {
        View view = findView(viewId);
        view.setLayoutParams(para);
        return this;
    }


    public RecycleHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView image = findView(viewId);
        image.setImageBitmap(bitmap);
        return this;
    }

    public RecycleHolder setGlideImage(int viewId, String url,int placehodlerImg,Activity activity){
        ImageView image = findView(viewId);
        Glide.with(activity)
                .load(url)
                .error(placehodlerImg)
                .placeholder(placehodlerImg)
                .into(image);
        return this;
    }

    public RecycleHolder setImageNet(int viewId, String url) {
        ImageView image = findView(viewId);
        //使用你所用的网络框架等
        return this;
    }

    public RecycleHolder setBackgroundColor(int viewId, int color) {
        View view = findView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RecycleHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = findView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RecycleHolder setVisible(int viewId, boolean visible) {
        View image = findView(viewId);
        image.setVisibility(visible ? View.VISIBLE : View.GONE);
        //使用你所用的网络框架等
        return this;
    }

    public RecycleHolder setVisible_invisible(int viewId, boolean visible) {
        View image = findView(viewId);
        image.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        //使用你所用的网络框架等
        return this;
    }

    public RecycleHolder setProgesss(int viewId, int percent) {
        ProgressBar progressBar = findView(viewId);
        progressBar.setProgress(percent);
        return this;
    }

    public boolean getVisible(int viewId) {
        View view = findView(viewId);
        return view.getVisibility() == View.VISIBLE;
    }


}
