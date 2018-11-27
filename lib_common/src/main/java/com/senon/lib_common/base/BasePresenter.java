package com.senon.lib_common.base;


/**
 * BasePresenter
 */
public abstract class BasePresenter<V extends BaseViewImp>{

    private V mView;

    public V getView(){
        return mView;
    }

    public void attachView(V v){
        mView = v;
    }

    public void detachView(){
        mView = null;
    }


}
