package com.senon.lib_common.utils;

import com.senon.lib_common.base.BaseViewImp;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 在presenter中使用生命周期管理，防止内存泄漏
 */
public class RxUtils {

    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseViewImp view) {
        if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindToLifecycle();
        } else if (view instanceof RxFragment) {
            return ((RxFragment) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }

    public static <T> LifecycleTransformer<T> bindActivityUntilEvent(BaseViewImp view,ActivityEvent event) {
        if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindUntilEvent(event);
        } else {
            throw new IllegalArgumentException("view isn't activity");
        }
    }

    public static <T> LifecycleTransformer<T> bindFragmentUntilEvent(BaseViewImp view,FragmentEvent event) {
        if (view instanceof RxFragment) {
            return ((RxFragment) view).bindUntilEvent(event);
        } else {
            throw new IllegalArgumentException("view isn't fragment");
        }
    }

    public static <T> ObservableTransformer<T, T> getSchedulerTransformer(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
