package com.lbw.jphoto.ui.model

import android.support.annotation.NonNull
import com.lbw.jphoto.service.HttpResult
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.service.ServiceFactory
import com.lbw.jphoto.service.TransformUtils
import com.lbw.jphoto.service.TransformUtils.main_io
import com.lzx.nickphoto.bean.PhotoInfo
import com.lzx.nickphoto.utils.network.PhotoServer

/**
 * Created by del on 17/7/26.
 */
class MainModel {


    companion object {   //companion object  包裹的内容是静态的
        val per_page: Int = 15
    }

    fun getAllPhotoList(page: Int,httpResult: HttpResultObserver<ArrayList<PhotoInfo>>) {
        ServiceFactory.instance.createService(PhotoServer::class.java)
                .getAllPhoto(page, per_page)
                .compose(TransformUtils.main_io())
                .subscribe(httpResult)
    }
}