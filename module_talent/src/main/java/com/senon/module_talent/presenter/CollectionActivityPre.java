package com.senon.module_talent.presenter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.CollectionArticle;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_talent.contract.CollectionActivityCon;

/**
 * CollectionActivity  P
 */
public class CollectionActivityPre extends CollectionActivityCon.Presenter{

    private Context context;

    public CollectionActivityPre(Context context) {
        this.context = context;
    }

    @Override
    public void getData(int page,boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getCollectList(page)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<CollectionArticle>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<CollectionArticle>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<CollectionArticle>>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse<CollectionArticle> baseResponse) {
                        super.onNext(baseResponse);

                        getView().getDataResult(baseResponse);
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public void getUnollect(final int id,int originId, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getUncollect(id,originId)
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
