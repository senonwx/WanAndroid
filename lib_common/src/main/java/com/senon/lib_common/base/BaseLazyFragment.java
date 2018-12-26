package com.senon.lib_common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.senon.lib_common.utils.LogUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * https://juejin.im/post/5adcb0e36fb9a07aa7673fbc
 * BaseLazyFragment 单fragment懒加载
 *
 *  * 生命周期执行的方法 如下：
 * 第一次生成页面-->可见
 * setUserVisibleHint: ----->false
 * setUserVisibleHint: ----->true
 * onCreateView: -----> onCreateView
 * onStart: -----> onStart
 * onFragmentFirst: 首次可见
 * onFragmentFirst: -----> 子fragment进行初始化操作
 * onResume: -----> onResume
 *
 * 可见-->第一次隐藏：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 *
 * 未销毁且不可见-->重新可见：
 * onStart: -----> onStart
 * onFragmentVisble: 可见
 * onFragmentVisble: -----> 子fragment每次可见时的操作
 * onResume: -----> onResume
 *
 * 可见-->销毁：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 * onDestroyView: -----> onDestroyView
 *
 * 我们可以更具以上生命周期来操作不同的业务逻辑，
 * 请务必运行此module demo，观看打印日志来自定义逻辑。
 */
public abstract class BaseLazyFragment<V extends BaseViewImp, P extends BasePresenter<V>> extends RxFragment {
    //引用V层和P层
    private P presenter;
    private V view;
    public Context mContext;

    private View rootView;
    private boolean mIsFirstVisible = true;/*当前Fragment是否首次可见，默认是首次可见**/
    private boolean isViewCreated = false;/*当前Fragment的View是否已经创建**/
    private boolean currentVisibleState = false;/*当前Fragment的可见状态，一种当前可见，一种当前不可见**/

    public P getPresenter() {
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("-----> onCreateView");
        if(rootView == null){
            rootView = inflater.inflate(getLayoutId(), container, false);
            mContext = getActivity();
            if (presenter == null) {
                presenter = createPresenter();
            }
            if (this.view == null) {
                this.view = createView();
            }
            if (presenter != null && view != null) {
                presenter.attachView(this.view);
            }
            init(rootView);
        }
        isViewCreated=true;//在onCreateView执行完毕，将isViewCreated改为true;
        return rootView;
    }

    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract P createPresenter();
    public abstract V createView();
    public abstract void init(View rootView);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("----->"+isVisibleToUser);
        if (isViewCreated) {
            //Fragment可见且状态不是可见(从一个Fragment切换到另外一个Fragment,后一个设置状态为可见)
            if (isVisibleToUser && !currentVisibleState) {
                disPatchFragment(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                //Fragment不可见且状态是可见(从一个Fragment切换到另外一个Fragment,前一个更改状态为不可见)
                disPatchFragment(false);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e("-----> onStart");
        //isHidden()是Fragment是否处于隐藏状态和isVisible()有区别
        //getUserVisibleHint(),Fragement是否可见
        if(!isHidden()&& getUserVisibleHint()){//如果Fragment没有隐藏且可见
            //执行分发的方法,三种结果对应自Fragment的三个回调，对应的操作，Fragment首次加载，可见，不可见
            disPatchFragment(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("-----> onResume");
        if(!mIsFirstVisible){
            //表示点击home键又返回操作,设置可见状态为ture
            if(!isHidden()&& !getUserVisibleHint() && currentVisibleState){
                disPatchFragment(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e("-----> onPause");
        //表示点击home键,原来可见的Fragment要走该方法，更改Fragment的状态为不可见
        if(!isHidden()&& getUserVisibleHint()){
            disPatchFragment(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e("-----> onDestroyView");
        //当 View 被销毁的时候我们需要重新设置 isViewCreated mIsFirstVisible 的状态
        isViewCreated = false;
        mIsFirstVisible = true;

        if (presenter != null) {
            presenter.detachView();
        }
    }


    /**
     * @param visible Fragment当前是否可见，然后调用相关方法
     */
    public void disPatchFragment(boolean visible){
        currentVisibleState=visible;
        if(visible){//Fragment可见
            if(mIsFirstVisible){//可见又是第一次
                mIsFirstVisible=false;//改变首次可见的状态
                onFragmentFirst();
            }else{//可见但不是第一次
                onFragmentVisble();
            }
        }else {//不可见
            onFragmentInVisible();
        }
    }

    //Fragemnet首次可见的方法
    public void onFragmentFirst(){
        LogUtils.e("首次可见");
    }
    //Fragemnet可见的方法
    public void onFragmentVisble(){//子Fragment调用次方法，执行可见操作
        LogUtils.e("可见");
    }
    //Fragemnet不可见的方法
    public void onFragmentInVisible(){
        LogUtils.e("不可见");
    }

}
