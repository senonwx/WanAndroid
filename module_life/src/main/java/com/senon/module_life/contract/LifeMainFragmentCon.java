package com.senon.module_life.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.KnowledgeSystem;

import java.util.HashMap;
import java.util.List;

/**
 * LifeMainFragmentCon  V 、P契约类
 */
public interface LifeMainFragmentCon {

    interface View extends BaseViewImp {

        void getKnowledgeListResult(BaseResponse<List<KnowledgeSystem>> data);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getKnowledgeList(boolean isDialog, boolean cancelable);

    }
}
