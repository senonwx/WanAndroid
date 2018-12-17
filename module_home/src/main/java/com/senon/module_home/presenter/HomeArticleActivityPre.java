package com.senon.module_home.presenter;

import android.content.Context;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_home.contract.HomeArticleActivityCon;
import java.util.List;

/**
 * HomeArticleActivityCon  P
 */
public class HomeArticleActivityPre extends HomeArticleActivityCon.Presenter{

    private Context context;

    public HomeArticleActivityPre(Context context) {
        this.context = context;
    }

    @Override
    public void getData(int page,boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getHomeArticle(page)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<HomeArticle>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<HomeArticle>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<HomeArticle>>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse<HomeArticle> baseResponse) {
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
