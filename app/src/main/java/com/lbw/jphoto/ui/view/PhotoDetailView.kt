package com.lbw.jphoto.ui.view

import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.bean.StatisticsInfo


/**
 * Created by del on 17/7/26.
 */
interface PhotoDetailView {

    fun OnGetPhotoDetailSuccese(result:PhotoInfo)

    fun OnGetPhotoStatisticsSuccese(result:StatisticsInfo)

    fun OnPhotoDetailError(e:Throwable)
    fun OnPhotoStatisticsError(e:Throwable)

}