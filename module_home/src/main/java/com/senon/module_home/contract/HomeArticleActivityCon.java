package com.senon.module_home.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.bean.HomeArticle;


public interface HomeArticleActivityCon {

    interface View extends BaseViewImp {

        void getDataResult(BaseResponse<HomeArticle> data);

        void getCollectResult(int id,boolean isCollect);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getData(int page,boolean isDialog, boolean cancelable);

        public abstract void getCollect(int id ,boolean isDialog, boolean cancelable);

        public abstract void getUnollect(int id, boolean isDialog, boolean cancelable);
    }
}
