package com.senon.module_art.presenter;

import android.content.Context;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.bean.WXarticle;
import com.senon.lib_common.bean.WXchapters;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_art.contract.ArtMainFragmentCon;
import java.util.List;

/**
 * HomeMainFragmentCon  P
 */
public class ArtMainFragmentPre extends ArtMainFragmentCon.Presenter{

    private Context context;
    public ArtMainFragmentPre(Context context) {
        this.context = context;
    }

    @Override
    public void getWXarticleChapters(boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getWXarticleChapters()
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<List<WXchapters>>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<List<WXchapters>>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<List<WXchapters>>>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse<List<WXchapters>> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getWXartChapResult(baseResponse);
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
    public void getWXarticleList(int id,int page, boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getWXarticleList(id,page)
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<WXarticle>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<WXarticle>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<WXarticle>>(context, RxErrorHandler.getInstance(),isDialog,cancelable) {
                    @Override
                    public void onNext(BaseResponse<WXarticle> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getWXartListResult(baseResponse);
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
