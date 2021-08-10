package com.fly.tomato.common.http.observer;

import com.fly.tomato.common.http.base.BaseObserver;
import com.fly.tomato.common.http.exception.ApiException;

import io.reactivex.disposables.Disposable;


/**
 * Created by Allen on 2017/10/31.
 *
 * @author Allen
 * <p>
 * 自定义Observer 处理string回调
 */

public abstract class StringObserver extends BaseObserver<String> {

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(String data);


    @Override
    public void doOnSubscribe(Disposable d) {
    }


    @Override
    public void onHttpError(ApiException apiException) {
        String errorMsg = apiException.getMessage();
        onError(errorMsg);
    }

    @Override
    public void doOnNext(String string) {
        onSuccess(string);
    }


    @Override
    public void doOnCompleted() {
    }

}
