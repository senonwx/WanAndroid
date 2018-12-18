package com.senon.lib_common.common.contract;


import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.bean.Login;

import java.util.HashMap;

/**
 * WebviewContract
 */
public interface WebviewContract {

    //方法命名以 请求方法+Result  命名
    interface View extends BaseViewImp {

        void getDataResult(BaseResponse data);


    }

    //方法命名以 get+方法  命名
    abstract class Presenter extends BasePresenter<View> {

        public abstract void getCollect(int id, boolean isDialog, boolean cancelable);

        public abstract void getUnollect(int id, boolean isDialog, boolean cancelable);


    }
}
