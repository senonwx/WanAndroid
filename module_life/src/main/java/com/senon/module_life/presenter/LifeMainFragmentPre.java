package com.senon.module_life.presenter;

import android.content.Context;

import com.senon.lib_common.bean.KnowledgeSystem;
import com.senon.lib_common.bean.Login;
import com.senon.lib_common.net.ServerUtils;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.net.callback.RequestCallback;
import com.senon.lib_common.net.callback.RxErrorHandler;
import com.senon.lib_common.utils.RetryWithDelay;
import com.senon.lib_common.utils.RxUtils;
import com.senon.lib_common.utils.ToastUtil;
import com.senon.module_life.contract.LifeMainFragmentCon;

import java.util.HashMap;
import java.util.List;

/**
 * LifeMainFragmentCon  P
 */
public class LifeMainFragmentPre extends LifeMainFragmentCon.Presenter{

    private Context context;

    public LifeMainFragmentPre(Context context) {
        this.context = context;
    }


    @Override
    public void getKnowledgeList(boolean isDialog, boolean cancelable) {
        ServerUtils.getCommonApi().getKnowledgeList()
                .retryWhen(new RetryWithDelay(3,2))
                .compose(RxUtils.<BaseResponse<List<KnowledgeSystem>>>bindToLifecycle(getView()))
                .compose(RxUtils.<BaseResponse<List<KnowledgeSystem>>>getSchedulerTransformer())
                .subscribe(new RequestCallback<BaseResponse<List<KnowledgeSystem>>>(context, RxErrorHandler.getInstance(),true) {
                    @Override
                    public void onNext(BaseResponse<List<KnowledgeSystem>> baseResponse) {
                        super.onNext(baseResponse);
                        if(baseResponse.getCode() == 0){
                            getView().getKnowledgeListResult(baseResponse);
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
