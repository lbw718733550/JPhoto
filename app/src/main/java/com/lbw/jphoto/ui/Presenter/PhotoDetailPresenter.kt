package com.lbw.jphoto.ui.Presenter

import android.view.View
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.bean.StatisticsInfo
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.annotations.NonNull

/**
 * Created by del on 17/7/26.
 */
interface PhotoDetailPresenter {

    fun  getPhotoDetail(photoId:String,@NonNull transformer: LifecycleTransformer<PhotoInfo> )
    fun  getPhotoStatistics(photoId:String,@NonNull transformer: LifecycleTransformer<StatisticsInfo>)

}