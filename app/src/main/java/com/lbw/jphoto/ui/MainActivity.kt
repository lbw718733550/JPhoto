package com.lbw.jphoto.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.lbw.jphoto.MyApplication
import com.lbw.jphoto.R
import com.lbw.jphoto.adapter.BaseRecycleViewAdapter_LoadMore
import com.lbw.jphoto.adapter.Recycle_MainAdapter
import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.ui.Presenter.impl.MainPresenterImpl
import com.lbw.jphoto.ui.model.MainModel
import com.lbw.jphoto.ui.view.MainView
import com.lbw.jphoto.utils.FileUtil
import com.lbw.jphoto.utils.ToastUtil
import com.lbw.jphoto.widget.DownloadDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , MainView{

    override fun setContentViewId(): Int {
        return R.layout.activity_main
    }

    lateinit var mPresenter:MainPresenterImpl
    lateinit var mRxPermissions:RxPermissions

    lateinit var mAdapter:Recycle_MainAdapter
    var lastVisibleItem:Int?=null
    var mList:ArrayList<PhotoInfo> = ArrayList()


    override fun init() {
        mPresenter = MainPresenterImpl(this)
        mRxPermissions = RxPermissions(this)
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
        recycle_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = Recycle_MainAdapter(this, R.layout.item_main_photo, R.layout.item_footview, mList)
        recycle_view.adapter = mAdapter

        //加载更多
        recycle_view.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //滚动到最后一个的时候 加载更多
                if (newState === RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem!! + 1 === mAdapter.getItemCount()) {
                    mAdapter.changeMoreStatus(mAdapter.LOADING_MORE)
                    mPresenter.loadMorePhotoList()
                }
            }
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var lastPositions = intArrayOf(((recyclerView!!.layoutManager) as StaggeredGridLayoutManager).getSpanCount());

                lastVisibleItem = ((recyclerView!!.layoutManager) as StaggeredGridLayoutManager).findLastVisibleItemPositions(lastPositions)
            }
        })


        mAdapter.setOnItemClickListner(object:BaseRecycleViewAdapter_LoadMore.OnItemClickListner<PhotoInfo>{
            override fun onItemClickListner(v: View, position: Int, t: PhotoInfo) {
            }
        })

        mAdapter.setonMyClickListener(object :Recycle_MainAdapter.OnMyClickListener{
            //图片点击
            override fun onImageViewClick(v: View, position: Int, bean: PhotoInfo) {
                val intent: Intent = Intent(instance, PhotoDetailActivity::class.java)
                intent.putExtra("photoId", bean.id)
                intent.putExtra("photoUrl", bean.urls.small)
                intent.putExtra("photoColor", bean.color)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    instance.startActivity(intent)
                } else {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            instance as MainActivity,
                            Pair.create<View, String>(v, instance.getString(R.string.transition_photo)))
                    ActivityCompat.startActivity(instance, intent, options.toBundle())
                }
            }
            //下载
            override fun onDownClick(v: View, position: Int, bean: PhotoInfo) {
                downImage(bean, v)
            }
        })

    }

    /**
     * 下载图片
     */
    private fun downImage(bean: PhotoInfo, v: View) {
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .filter { aBoolean -> aBoolean }
                .filter {
                    if (FileUtil.isExistsImage(bean.links.download)) {
                        ToastUtil.showSnackBar(this@MainActivity, v,
                                "文件已经下载了哦,到 ../NickPhoto/download 查看吧")
                        return@filter false
                    }
                    return@filter true
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val downloadDialog: DownloadDialog = DownloadDialog()
                    val bundle: Bundle = Bundle()
                    bundle.putString("downloadUrl", bean.links.download)
                    downloadDialog.arguments = bundle
                    downloadDialog.show(supportFragmentManager, "DownloadDialog")
                }, {
                    ToastUtil.show(this@MainActivity, "下载失败,请重试")
                })
    }

    /**
     * 获取数据成功
     */
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

    /**
     * 加载数据成功
     */
    override fun loadMoreSuccess(result: ArrayList<PhotoInfo>) {
        mList.addAll(result)
        mAdapter.updaterall(mList)
        mAdapter.changeMoreStatus(mAdapter.PULLUP_LOAD_MORE)
    }


    override fun OnError(e: Throwable) {
        ToastUtil.show(MyApplication.instance,e.toString())
    }





}
