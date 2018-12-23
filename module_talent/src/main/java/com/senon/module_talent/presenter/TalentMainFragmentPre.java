package com.senon.module_talent.presenter;

import android.content.Context;
import com.senon.lib_common.bean.Login;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_talent.contract.TalentMainFragmentCon;
import java.util.HashMap;

/**
 * HomeMainFragmentCon  P
 */
public class TalentMainFragmentPre extends TalentMainFragmentCon.Presenter{

    private Context context;

    public TalentMainFragmentPre(Context context) {
        this.context = context;
    }

    @Override
    public void getLogout( boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().logout()
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        super.onNext(baseResponse);
                        BaseResponse response = baseResponse;
                        if(response.getCode()==0){
                            getView().getLogoutResult(response);
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
