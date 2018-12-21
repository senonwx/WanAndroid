package com.senon.module_talent.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.bean.CollectionArticle;


public interface CollectionActivityCon {

    interface View extends BaseViewImp {

        void getDataResult(BaseResponse<CollectionArticle> data);

        void getCollectResult(int id, boolean isCollect);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getData(int page,boolean isDialog, boolean cancelable);

        public abstract void getUnollect(int id,int originId, boolean isDialog, boolean cancelable);
    }
}
