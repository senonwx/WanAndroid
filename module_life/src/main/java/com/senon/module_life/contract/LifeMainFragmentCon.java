package com.senon.module_life.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.base.BaseResponse;
import java.util.HashMap;

/**
 * LifeMainFragmentCon  V 、P契约类
 */
public interface LifeMainFragmentCon {

    interface View extends BaseViewImp {

        void result(BaseResponse data);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getData(HashMap<String, String> map, boolean isDialog, boolean cancelable);

    }
}
