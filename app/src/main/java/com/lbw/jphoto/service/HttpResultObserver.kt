package com.lbw.jphoto.service

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by lin on 16/12/21.
 */
abstract class HttpResultObserver<T> : Observer<HttpResult<T>> {


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

    override fun onNext(t: HttpResult<T>) {
        checkNotNull(t)
        if (t.success) {
            onSuccess(t.contentData, t.message)
        } else {
            _onError(Throwable("error=" + t.success))
        }
    }

    abstract fun onSuccess(t: T?, message: String?)

    abstract fun _onError(e: Throwable)
}