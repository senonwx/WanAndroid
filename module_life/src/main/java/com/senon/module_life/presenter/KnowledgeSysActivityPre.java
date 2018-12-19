package com.senon.module_life.presenter;

import android.content.Context;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.KnowledgeSysArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_life.contract.KnowledgeSysActivityCon;

/**
 * KnowledgeSysActivity  P
 */
public class KnowledgeSysActivityPre extends KnowledgeSysActivityCon.Presenter{

    private Context context;

    public KnowledgeSysActivityPre(Context context) {
        this.context = context;
    }

    @Override
    public void getData(int cid ,int page,boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getKnowledgeArticle(page,cid)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<KnowledgeSysArticle>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<KnowledgeSysArticle>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<KnowledgeSysArticle>>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse<KnowledgeSysArticle> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getDataResult(baseResponse);
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
