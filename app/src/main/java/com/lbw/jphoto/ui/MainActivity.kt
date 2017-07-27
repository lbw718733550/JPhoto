package com.lbw.jphoto.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lbw.jphoto.R
import com.lbw.jphoto.adapter.Recycle_MainAdapter
import com.lbw.jphoto.service.HttpResult
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.ui.Presenter.MainPresenter
import com.lbw.jphoto.ui.Presenter.impl.MainPresenterImpl
import com.lbw.jphoto.ui.view.MainView
import com.lzx.nickphoto.bean.PhotoInfo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , MainView{
    override fun setContentViewId(): Int {
        return R.layout.activity_main
    }

    lateinit var mPresenter:MainPresenterImpl
    lateinit var mAdapter:Recycle_MainAdapter
    var mList:ArrayList<PhotoInfo> = ArrayList()


    override fun init() {
        mPresenter = MainPresenterImpl(this)


        //SwipeRefreshLayout
        refresh_layout.setColorSchemeResources(R.color.colorPrimaryDark)
        refresh_layout.setOnRefreshListener({
        })

        //RecycleView
        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager = LinearLayoutManager(this)
        mAdapter = Recycle_MainAdapter(this,R.layout.item_main_photo,mList)
        recycle_view.adapter = mAdapter

        mPresenter.getAllPhotoList()

    }

    override fun OnGetPhotoSuccese(result: ArrayList<PhotoInfo>) {
        mAdapter.updaterall(result)
    }

    override fun showPro(isShow: Boolean) {
        if(isShow) load_pro.visibility = View.VISIBLE else load_pro.visibility = View.GONE
    }




}
