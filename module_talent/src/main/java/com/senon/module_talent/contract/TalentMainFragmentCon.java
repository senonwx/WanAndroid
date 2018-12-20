package com.senon.module_talent.contract;

import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.base.BaseResponse;

import java.util.HashMap;

/**
 * TalentMainFragmentCon  V 、P契约类
 */
public interface TalentMainFragmentCon {

    interface View extends BaseViewImp {

        void getLogoutResult(BaseResponse data);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getLogout(boolean isDialog, boolean cancelable);

    }
}
