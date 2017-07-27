package com.lbw.jphoto.ui.Presenter.impl

import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.ui.Presenter.MainPresenter
import com.lbw.jphoto.ui.model.MainModel
import com.lbw.jphoto.ui.view.MainView
import com.lzx.nickphoto.bean.PhotoInfo

/**
 * Created by del on 17/7/26.
 */
class MainPresenterImpl (val mView:MainView): MainPresenter{

    var  page: Int = 1
    var mModel:MainModel = MainModel()

    override fun getAllPhotoList() {
        mModel.getAllPhotoList(page,object : HttpResultObserver<ArrayList<PhotoInfo>>() {
            override fun onSuccess(t: ArrayList<PhotoInfo>?, message: String?) {
                mView.OnGetPhotoSuccese(t!!)
            }

            override fun _onError(e: Throwable) {
            }

        })
    }

    override fun loadMorePhotoList() {
    }
}