package com.lbw.jphoto.ui.Presenter

import android.view.View
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.bean.PhotoInfo

/**
 * Created by del on 17/7/26.
 */
interface PhotoDetailPresenter {

    fun  getPhotoDetail(photoId:String)
    fun  getPhotoStatistics(photoId:String)

}