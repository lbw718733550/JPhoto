package com.lbw.jphoto.ui.model

import android.support.annotation.NonNull
import com.google.gson.Gson
import com.lbw.jphoto.service.HttpResult
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.service.ServiceFactory
import com.lbw.jphoto.service.TransformUtils
import com.lbw.jphoto.service.TransformUtils.main_io
import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.utils.network.PhotoServer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray

/**
 * Created by del on 17/7/26.
 */
class MainModel {


    companion object {   //companion object  包裹的内容是静态的
        val per_page: Int = 15
    }

    fun getAllPhotoList(page: Int,httpResult: HttpResultObserver<PhotoInfo>) {
        ServiceFactory.instance.createService(PhotoServer::class.java)
                .getAllPhoto(page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpResult)
    }
}

