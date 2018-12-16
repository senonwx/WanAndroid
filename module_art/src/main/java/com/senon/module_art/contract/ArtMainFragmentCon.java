package com.senon.module_art.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.bean.WXarticle;
import com.senon.lib_common.bean.WXchapters;

import java.util.List;

/**
 * ArtMainFragmentCon  V 、P契约类
 */
public interface ArtMainFragmentCon {

    interface View extends BaseViewImp {

        void getWXartChapResult(BaseResponse<List<WXchapters>> data);

        void getWXartListResult(BaseResponse<WXarticle> data);


    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getWXarticleChapters(boolean isDialog, boolean cancelable);

        public abstract void getWXarticleList(int id,int page,boolean isDialog, boolean cancelable);


    }
}
