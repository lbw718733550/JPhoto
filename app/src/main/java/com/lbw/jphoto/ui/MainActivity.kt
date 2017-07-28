package com.lbw.jphoto.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lbw.jphoto.MyApplication
import com.lbw.jphoto.R
import com.lbw.jphoto.adapter.BaseRecycleViewAdapter_LoadMore
import com.lbw.jphoto.adapter.Recycle_MainAdapter
import com.lbw.jphoto.service.HttpResult
import com.lbw.jphoto.service.HttpResultObserver
import com.lbw.jphoto.ui.Presenter.MainPresenter
import com.lbw.jphoto.ui.Presenter.impl.MainPresenterImpl
import com.lbw.jphoto.ui.model.MainModel
import com.lbw.jphoto.ui.view.MainView
import com.lbw.jphoto.utils.ToastUtil
import com.lzx.nickphoto.bean.PhotoInfo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , MainView{

    override fun setContentViewId(): Int {
        return R.layout.activity_main
    }

    lateinit var mPresenter:MainPresenterImpl

    lateinit var mAdapter:Recycle_MainAdapter
    var lastVisibleItem:Int?=null
    var mList:ArrayList<PhotoInfo> = ArrayList()
    override fun init() {
        mPresenter = MainPresenterImpl(this)
        listInit()
        mPresenter.getAllPhotoList(true)

    }


    private fun listInit() {
        //SwipeRefreshLayout
        refresh_layout.setColorSchemeResources(R.color.colorPrimaryDark)
        refresh_layout.setOnRefreshListener({
            mPresenter.getAllPhotoList(false)
        })

        //RecycleView
        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager = LinearLayoutManager(this)
        mAdapter = Recycle_MainAdapter(this, R.layout.item_main_photo, R.layout.item_footview, mList)
        recycle_view.adapter = mAdapter

        //加载更多
        recycle_view.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState === RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem!! + 1 === mAdapter.getItemCount()) {
                    mAdapter.changeMoreStatus(mAdapter.LOADING_MORE)
                    mPresenter.loadMorePhotoList()
                }
            }
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = ((recyclerView!!.layoutManager) as LinearLayoutManager).findLastVisibleItemPosition()
            }
        })


        mAdapter.setOnItemClickListner(object:BaseRecycleViewAdapter_LoadMore.OnItemClickListner<PhotoInfo>{
            override fun onItemClickListner(v: View, position: Int, t: PhotoInfo) {
                
            }
        })
    }

    override fun OnGetPhotoSuccese(result: ArrayList<PhotoInfo>) {
        refresh_layout.isRefreshing = false
        mList.addAll(result)
        mAdapter.updaterall(result)
        if (result.size >= MainModel.per_page){
            mAdapter.changeMoreStatus(mAdapter.PULLUP_LOAD_MORE)
        }else{
            mAdapter.changeMoreStatus(mAdapter.NO_MORE_LOADING)
        }
    }

    override fun loadMoreSuccess(result: ArrayList<PhotoInfo>) {
        mList.addAll(result)
        mAdapter.updaterall(mList)
        mAdapter.changeMoreStatus(mAdapter.PULLUP_LOAD_MORE)
    }


    override fun OnError(e: Throwable) {
        ToastUtil.show(MyApplication.instance,e.toString())
    }





}
