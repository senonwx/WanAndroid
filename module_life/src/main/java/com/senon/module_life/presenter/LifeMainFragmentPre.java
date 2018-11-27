package com.senon.module_life.presenter;

import android.content.Context;
import com.senon.lib_common.login.Login;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.net.bean.BaseResponse;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_life.contract.LifeMainFragmentCon;

import java.util.HashMap;

/**
 * LifeMainFragmentCon  P
 */
public class LifeMainFragmentPre extends LifeMainFragmentCon.Presenter{

    private Context context;

    public LifeMainFragmentPre(Context context) {
        this.context = context;
    }

    @Override
    public void getData(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().login(map)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<Login>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<Login>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<Login>>(context, RxErrorHandler.getInstance(),true) {
                    @Override
                    public void onNext(BaseResponse<Login> baseResponse) {
                        super.onNext(baseResponse);
                        BaseResponse<Login> response = baseResponse;
                        if(response.isSuccess()){
//                            getView().resultLogin(response);
                        }else{
                            ToastUtil.initToast(response.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
