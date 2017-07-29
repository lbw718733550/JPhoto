package com.lbw.jphoto.ui.Presenter

import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.bean.PhotoInfo

/**
 * Created by del on 17/7/26.
 */
interface PhotoDetailPresenter {

    fun  getAllPhotoList(isShow:Boolean)
    fun  loadMorePhotoList()
}