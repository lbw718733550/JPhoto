package com.lbw.jphoto.ui.Presenter.impl

import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.bean.StatisticsInfo
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.ui.Presenter.PhotoDetailPresenter
import com.lbw.jphoto.ui.model.PhotoDetailModel
import com.lbw.jphoto.ui.view.PhotoDetailView

/**
 * Created by Administrator on 2017/7/30.
 */
class PhotoDrtailPersenterImpl (var mView:PhotoDetailView):PhotoDetailPresenter {

    var mModel:PhotoDetailModel = PhotoDetailModel()

    override fun getPhotoDetail(photoId: String) {
        mModel.getPhotoDetail(photoId,object :HttpResultObserver<PhotoInfo>(){
            override fun onSuccess(t: PhotoInfo?) {
                mView.OnGetPhotoDetailSuccese(t!!)
            }
            override fun _onError(e: Throwable) {
                mView.OnPhotoDetailError(e!!)
            }
            override fun _onStart() {
            }

        })
    }

    override fun getPhotoStatistics(photoId: String) {
        mModel.getPhotoStatistics(photoId,object :HttpResultObserver<StatisticsInfo>(){
            override fun onSuccess(t: StatisticsInfo?) {
                mView.OnGetPhotoStatisticsSuccese(t!!)
            }
            override fun _onError(e: Throwable) {
                mView.OnPhotoStatisticsError(e!!)
            }
            override fun _onStart() {
            }
        })
    }

}