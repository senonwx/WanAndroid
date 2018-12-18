package com.senon.module_home.presenter;

import android.content.Context;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_home.contract.HomeProjectActivityCon;

/**
 * HomeProjectActivityCon  P
 */
public class HomeProjectActivityPre extends HomeProjectActivityCon.Presenter{

    private Context context;

    public HomeProjectActivityPre(Context context) {
        this.context = context;
    }

    @Override
    public void getData(int page,boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getHomeProject(page)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<ProjectArticle>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<ProjectArticle>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<ProjectArticle>>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse<ProjectArticle> baseResponse) {
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

}
