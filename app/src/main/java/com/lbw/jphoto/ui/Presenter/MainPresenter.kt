package com.lbw.jphoto.ui.Presenter

import com.lbw.jphoto.service.HttpResultObserver
import com.lzx.nickphoto.bean.PhotoInfo

/**
 * Created by del on 17/7/26.
 */
interface MainPresenter {

    fun  getAllPhotoList(isShow:Boolean)
    fun  loadMorePhotoList()
}