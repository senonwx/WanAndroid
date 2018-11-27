package com.senon.lib_common.net.callback;

import android.content.Context;
import com.senon.lib_common.net.progress.ProgressCancelListener;
import com.senon.lib_common.net.progress.ProgressDialogHandler;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * RxJava 自定义回调
 */
public abstract class RequestCallback<T> implements Observer<T>,ProgressCancelListener {

    private ErrorListener errorListener;
    private Disposable mDisposable;
    private Context mContext;
    private boolean cancelable = false;//该dialog如果开启 则可以设置是否能够返回键取消请求
    private ProgressDialogHandler mProgressDialogHandler;//默认为null  即不开启dialog

    public RequestCallback(Context context,ErrorListener errorListener) {
        this(context,errorListener,false);
    }

    /**
     * 传这个构造默认开启dialog
     * @param cancelable 该dialog如果开启 则可以设置是否能够返回键取消请求
     */
    public RequestCallback(Context context,ErrorListener errorListener,boolean cancelable) {
        this.mContext = context;
        this.errorListener = errorListener;
        this.cancelable = cancelable;
        if(cancelable){
            this.mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.mDisposable = d;
        showProgressDialog();

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        dismissProgressDialog();

        if (errorListener != null) {
            errorListener.handleError(e);
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();

    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
