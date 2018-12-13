package com.senon.lib_common.common.login;

import android.content.Context;

import com.senon.lib_common.bean.Login;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import java.util.HashMap;

/**
 * 登录 P
 */
public class LoginPresenter extends LoginContract.Presenter {

    private Context context;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getLogin(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().login(map)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<Login>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<Login>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<Login>>(context, RxErrorHandler.getInstance(),true) {
                    @Override
                    public void onNext(BaseResponse<Login> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getLoginResult(baseResponse);
                        }else{
                            ToastUtil.initToast(baseResponse.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public void getRegister(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().register(map)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<Login>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<Login>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<Login>>(context, RxErrorHandler.getInstance(),true) {
                    @Override
                    public void onNext(BaseResponse<Login> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getRegisterResult(baseResponse);
                        }else{
                            ToastUtil.initToast(baseResponse.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }


}
