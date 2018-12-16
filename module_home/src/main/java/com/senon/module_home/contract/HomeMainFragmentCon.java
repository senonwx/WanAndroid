package com.senon.module_home.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;

import java.util.List;

/**
 * HomeMainFragmentCon  V 、P契约类
 */
public interface HomeMainFragmentCon {

    interface View extends BaseViewImp {

        void getBannerResult(BaseResponse<List<Banner>> data);

        void getHomeArticleResult(BaseResponse<HomeArticle> data);

        void getHomeProjectResult(BaseResponse<ProjectArticle> data);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getBanner(boolean isDialog, boolean cancelable);

        public abstract void getHomeArticle(int page,boolean isDialog, boolean cancelable);

        public abstract void getHomeProject(int page,boolean isDialog, boolean cancelable);

    }
}
