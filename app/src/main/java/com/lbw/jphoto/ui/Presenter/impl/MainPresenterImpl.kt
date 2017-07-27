package com.lbw.jphoto.ui.Presenter.impl

import android.widget.Toast
import com.lbw.jphoto.MyApplication
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.ui.Presenter.MainPresenter
import com.lbw.jphoto.ui.model.MainModel
import com.lbw.jphoto.ui.view.MainView
import com.lzx.nickphoto.bean.PhotoInfo
import okhttp3.ResponseBody

/**
 * Created by del on 17/7/26.
 */
class MainPresenterImpl (val mView:MainView): MainPresenter{

    var  page: Int = 1
    var mModel:MainModel = MainModel()

    override fun getAllPhotoList() {
        mModel.getAllPhotoList(page,object : HttpResultObserver<ResponseBody>() {
            override fun onSuccess(t: ResponseBody?) {

                Toast.makeText(MyApplication.instance,t.toString(),Toast.LENGTH_LONG).show()
            }


            override fun _onError(e: Throwable) {
            }

        })
    }

    override fun loadMorePhotoList() {
    }
}