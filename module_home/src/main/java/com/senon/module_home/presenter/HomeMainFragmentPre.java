package com.senon.module_home.presenter;

import android.content.Context;

import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.Login;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_home.contract.HomeMainFragmentCon;

import java.util.List;

/**
 * HomeMainFragmentCon  P
 */
public class HomeMainFragmentPre extends HomeMainFragmentCon.Presenter{

    private Context context;

    public HomeMainFragmentPre(Context context) {
        this.context = context;
    }

    @Override
    public void getBanner(boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().banner()
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<List<Banner>>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<List<Banner>>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<List<Banner>>>(context, RxErrorHandler.getInstance(),true) {
                    @Override
                    public void onNext(BaseResponse<List<Banner>> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getBannerResult(baseResponse);
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
    public void getHomeArticle(int page, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getHomeArticle(page)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<HomeArticle>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<HomeArticle>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<HomeArticle>>(context, RxErrorHandler.getInstance(),true) {
                    @Override
                    public void onNext(BaseResponse<HomeArticle> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getHomeArticleResult(baseResponse);
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
    public void getProjectList(int page, int cid, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getProjectList(page,cid)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<ProjectArticle>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<ProjectArticle>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<ProjectArticle>>(context, RxErrorHandler.getInstance(),true) {
                    @Override
                    public void onNext(BaseResponse<ProjectArticle> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getProjectListResult(baseResponse);
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
