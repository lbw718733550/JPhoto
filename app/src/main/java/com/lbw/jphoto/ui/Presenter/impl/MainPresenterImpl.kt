package com.lbw.jphoto.ui.Presenter.impl

import android.widget.Toast
import com.google.gson.Gson
import com.lbw.jphoto.MyApplication
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.ui.Presenter.MainPresenter
import com.lbw.jphoto.ui.model.MainModel
import com.lbw.jphoto.ui.view.MainView
import com.lbw.jphoto.utils.ToastUtil
import com.lbw.jphoto.bean.PhotoInfo
import okhttp3.ResponseBody
import org.json.JSONArray

/**
 * Created by del on 17/7/26.
 */
class MainPresenterImpl (val mView:MainView): MainPresenter{

    var  page: Int = 1
    var mModel:MainModel = MainModel()
    var isMore: Boolean = false

    override fun getAllPhotoList(isShow:Boolean) {
        mModel.getAllPhotoList(page,object : HttpResultObserver<ArrayList<PhotoInfo>>() {
            override fun _onStart() {
            }
            override fun onSuccess(result: ArrayList<PhotoInfo>?) {
                mView.OnGetPhotoSuccese(result!!)
                isMore = result.size >= MainModel.per_page
            }
            override fun _onError(e: Throwable) {
                mView.OnError(e!!)
            }
        })
    }

    override fun loadMorePhotoList() {
        if (isMore) {
            page++
            mModel.getAllPhotoList(page,object : HttpResultObserver<ArrayList<PhotoInfo>>() {
                override fun _onStart() {
                }
                override fun onSuccess(t: ArrayList<PhotoInfo>?) {
                    mView.loadMoreSuccess(t!!)
                    isMore = t.size >= MainModel.per_page
                }
                override fun _onError(e: Throwable) {
                    mView.OnError(e!!)
                }
            })
        }
    }
}