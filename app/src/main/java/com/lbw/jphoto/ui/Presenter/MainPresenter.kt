package com.lbw.jphoto.ui.Presenter

import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.bean.PhotoInfo
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.annotations.NonNull

/**
 * Created by del on 17/7/26.
 */
interface MainPresenter {

    fun  getAllPhotoList(isShow:Boolean,@NonNull transformer: LifecycleTransformer<ArrayList<PhotoInfo>>)
    fun  loadMorePhotoList(@NonNull transformer: LifecycleTransformer<ArrayList<PhotoInfo>>)
}