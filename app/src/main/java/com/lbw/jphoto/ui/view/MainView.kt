package com.lbw.jphoto.ui.view

import com.lbw.jphoto.bean.PhotoInfo


/**
 * Created by del on 17/7/26.
 */
interface MainView {

    fun OnGetPhotoSuccese(result:ArrayList<PhotoInfo>)

    fun loadMoreSuccess(result:ArrayList<PhotoInfo>)

    fun OnError(e:Throwable)

}