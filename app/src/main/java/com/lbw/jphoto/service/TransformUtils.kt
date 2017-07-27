package com.lbw.jphoto.service

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by lin on 16/12/21.
 */
object TransformUtils {


    fun <T> main_io(): ObservableTransformer<T, T> {
        return ObservableTransformer { tObservable -> tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()) }
    }

    fun <T> all_io(): ObservableTransformer<T, T> {
        return ObservableTransformer { tObservable -> tObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io()) }
    }
}
