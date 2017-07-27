package com.lbw.jphoto.service

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody

/**
 * Created by lin on 16/12/21.
 */
abstract class HttpResultObserver<T> : Observer<ResponseBody> {


    override fun onError(e: Throwable?) {
        if (e != null) {
            e.printStackTrace()
            if (e.message == null) {
                _onError(Throwable(e.toString()))
            } else {
                _onError(Throwable(e.message))
            }
        } else {
            _onError(Exception("null message"))
        }
    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: ResponseBody) {
        checkNotNull(t)
            onSuccess(t)
    }

    abstract fun onSuccess(t: ResponseBody?)

    abstract fun _onError(e: Throwable)
}