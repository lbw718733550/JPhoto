package com.lbw.jphoto.service

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.observers.ResourceObserver
import okhttp3.ResponseBody

/**
 * Created by lin on 16/12/21.
 */
abstract class HttpResultObserver<T> : ResourceObserver<T>() {


    override fun onError(@NonNull e: Throwable) {
                _onError(e!!)
    }

    override fun onComplete() {
    }
    override fun onStart() {
        super.onStart()
        _onStart()
    }


    override fun onNext(@NonNull t: T) {
            onSuccess(t!!)
    }

    abstract fun onSuccess(t:T?)
    abstract fun _onError(e: Throwable)
    abstract fun _onStart()

}