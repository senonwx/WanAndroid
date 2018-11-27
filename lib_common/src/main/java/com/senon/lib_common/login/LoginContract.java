package com.senon.lib_common.login;


import com.senon.lib_common.base.BasePresenter;
import com.senon.lib_common.base.BaseViewImp;
import com.senon.lib_common.net.bean.BaseResponse;
import java.util.HashMap;

/**
 * LoginContract
 */
public interface LoginContract {

    //方法命名以 result+方法  命名
    interface View extends BaseViewImp {

        void resultLogin(BaseResponse<Login> data);

        void resultVerifyPhoneIsRsg(BaseResponse<Login> data);

    }

    //方法命名以 get+方法  命名
    abstract class Presenter extends BasePresenter<View> {

        public abstract void getLogin(HashMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getVerifyPhoneIsRsg(HashMap<String, String> map, boolean isDialog, boolean cancelable);

    }
}
