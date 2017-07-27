package com.lbw.jphoto.ui.view

import com.lzx.nickphoto.bean.PhotoInfo

/**
 * Created by del on 17/7/26.
 */
interface MainView {

    fun OnGetPhotoSuccese(result:ArrayList<PhotoInfo>)

    fun showPro(isShow:Boolean)
}