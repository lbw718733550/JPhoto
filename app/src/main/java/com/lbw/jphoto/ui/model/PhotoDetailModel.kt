package com.lbw.jphoto.ui.model

import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.bean.StatisticsInfo
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.service.ServiceFactory
import com.lbw.jphoto.utils.network.PhotoServer
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Created by Administrator on 2017/7/29.
 */
class PhotoDetailModel{


    /**
     * 请求详情
     */
    fun getPhotoDetail(photoId:String, @NonNull transformer: LifecycleTransformer<PhotoInfo>,@NonNull httpResult:HttpResultObserver<PhotoInfo>){
        ServiceFactory.instance.createService(PhotoServer::class.java)
                .getPhotoDetail(photoId)
                .compose(transformer)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpResult)
    }

    /**
     * 请求统计
     */
    fun getPhotoStatistics(photoId:String, @NonNull transformer: LifecycleTransformer<StatisticsInfo>,@NonNull httpResult:HttpResultObserver<StatisticsInfo>){
        ServiceFactory.instance.createService(PhotoServer::class.java)
                .getPhotoStatistics(photoId)
                .compose(transformer)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpResult)
    }

}
