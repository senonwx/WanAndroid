package com.senon.module_home.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.bean.ProjectArticle;


public interface HomeProjectActivityCon {

    interface View extends BaseViewImp {

        void getDataResult(BaseResponse<ProjectArticle> data);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getData(int page,boolean isDialog, boolean cancelable);


    }
}
