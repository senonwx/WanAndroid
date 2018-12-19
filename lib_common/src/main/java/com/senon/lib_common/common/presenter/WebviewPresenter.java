package com.senon.lib_common.common.presenter;

import android.content.Context;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Login;
import com.senon.lib_common.common.contract.WebviewContract;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import java.util.HashMap;

/**
 * Webview P
 */
public class WebviewPresenter extends WebviewContract.Presenter {

    private Context context;
    public WebviewPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getCollect(final int id, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getCollect(id)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getCollectResult(id,true);
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
    public void getUnollect(final int id, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getUncollectOriginId(id)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getCollectResult(id,false);
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
