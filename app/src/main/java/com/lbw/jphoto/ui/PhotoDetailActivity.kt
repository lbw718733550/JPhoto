package com.lbw.jphoto.ui

import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.jakewharton.rxbinding2.view.RxView
import com.lbw.jphoto.R
import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.bean.StatisticsInfo
import com.lbw.jphoto.service.ImageLoadUtil
import com.lbw.jphoto.ui.Presenter.impl.MainPresenterImpl
import com.lbw.jphoto.ui.Presenter.impl.PhotoDrtailPersenterImpl
import com.lbw.jphoto.ui.view.PhotoDetailView
import com.lbw.jphoto.utils.FileUtil
import com.lbw.jphoto.utils.ToastUtil
import com.lbw.jphoto.widget.DownloadDialog
import kotlinx.android.synthetic.main.activity_photo_detail.*
import com.lbw.jphoto.widget.ParallaxScrollView
import com.lbw.jphoto.widget.ProDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_photo_detail.*

/**
 * Created by del on 17/7/28.
 */
class PhotoDetailActivity : BaseActivity() ,View.OnClickListener,PhotoDetailView{



    override fun setContentViewId(): Int {
        return R.layout.activity_photo_detail
    }

     lateinit var photoId: String
     lateinit var photoUrl: String
     lateinit var photoColor: String
     lateinit var mPhotoInfo: PhotoInfo
     lateinit var bitmap:Bitmap

     lateinit var mWallpaperManager: WallpaperManager  //壁纸管理器

    lateinit var mPresenter: PhotoDrtailPersenterImpl
    public lateinit var mRxPermissions: RxPermissions

    override fun init() {
        mPresenter = PhotoDrtailPersenterImpl(this)
        mRxPermissions = RxPermissions(this)

        photoId = intent.getStringExtra("photoId")
        photoUrl = intent.getStringExtra("photoUrl")
        photoColor = intent.getStringExtra("photoColor")
        bitmap = intent.getBundleExtra("bitmap").getParcelable<Bitmap>("bitmap")

        //加载图片
//        ImageLoadUtil.ImageloadWithListener(this,photoUrl,photoColor,photoColor,image_photo)
        image_photo.setImageBitmap(bitmap)

        mWallpaperManager = WallpaperManager.getInstance(this)


        btnBack.setOnClickListener (this)
        detail_size.setOnClickListener(this)
        detail_time.setOnClickListener(this)
        detail_color.setOnClickListener(this)
        detail_aperture.setOnClickListener(this)
        detail_location.setOnClickListener(this)
        detail_focal_length.setOnClickListener(this)
        detail_camera.setOnClickListener(this)
        detail_exposure.setOnClickListener(this)
        btn_share.setOnClickListener(this)
        btn_wallpaper.setOnClickListener(this)
        detail_likes.setOnClickListener(this)
        detail_see.setOnClickListener(this)
        detail_download.setOnClickListener(this)
        detail_likes_text.setOnClickListener(this)
        detail_see_text.setOnClickListener(this)
        detail_download_text.setOnClickListener(this)

        //视差效果
        myscrollView.setScrollViewListener(object : ParallaxScrollView.ScrollviewListener {
            override fun onScrollChanged(scrollView: ParallaxScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                image_photo.scrollTo(x, -y / 3)
            }
        })

        mPresenter.getPhotoDetail(photoId)
        mPresenter.getPhotoStatistics(photoId)
    }


    override fun onClick(v: View?) {
        when(v){
            detail_size -> ToastUtil.showSnackBar(this, detail_size, "尺寸：" + detail_size.text)
            detail_time -> ToastUtil.showSnackBar(this, detail_time, "快门时间：" + detail_time.text)
            detail_color -> ToastUtil.showSnackBar(this, detail_color, "颜色：" + detail_color.text)
            detail_aperture -> ToastUtil.showSnackBar(this, detail_aperture, "光圈：" + detail_aperture.text)
            detail_location -> ToastUtil.showSnackBar(this, detail_location, "位置：" + detail_location.text)
            detail_focal_length -> ToastUtil.showSnackBar(this, detail_focal_length, "焦距：" + detail_focal_length.text)
            detail_camera -> ToastUtil.showSnackBar(this, detail_camera, "器材：" + detail_camera.text)
            detail_exposure -> ToastUtil.showSnackBar(this, detail_exposure, "曝光率：" + detail_exposure.text)
            detail_likes, detail_likes_text -> ToastUtil.showSnackBar(this, detail_likes, "喜欢：" + detail_likes.text)
            detail_see, detail_see_text -> ToastUtil.showSnackBar(this, detail_see, "浏览次数：" + detail_see.text)
            detail_download, detail_download_text -> ToastUtil.showSnackBar(this, detail_size, "下载次数：" + detail_download.text)
            btnBack -> baseFinish()
            btn_share -> shareMsg()
            btn_wallpaper -> setWallpaper()
        }
    }

    override fun OnGetPhotoDetailSuccese(result: PhotoInfo) {
        mPhotoInfo = result
        ImageLoadUtil.Imageload(this, mPhotoInfo.user.profile_image.large,mPhotoInfo.color,mPhotoInfo.color,userAvatar)
        nickName.text = "来自 " + mPhotoInfo.user.name
        photoTime.text = "拍摄于 " + mPhotoInfo.created_at.substring(0, mPhotoInfo.created_at.indexOf("T"))
        detail_size.text = mPhotoInfo.width + "x" + mPhotoInfo.height
        detail_time.text = mPhotoInfo.exif.exposure_time ?: "Unknown"
        detail_color.text = mPhotoInfo.color
        view_color.setBackgroundColor(Color.parseColor(mPhotoInfo.color))
        detail_aperture.text = mPhotoInfo.exif.aperture ?: "Unknown"
        var location: String = "Unknown"
        if (mPhotoInfo.location != null) {
            val city: String = mPhotoInfo.location.city ?: "Unknown"
            location = city + "," + mPhotoInfo.location.country
        }
        detail_location.text = location
        detail_focal_length.text = mPhotoInfo.exif.focal_length ?: "Unknown"
        detail_camera.text = mPhotoInfo.exif.model ?: "Unknown"
        detail_exposure.text = mPhotoInfo.exif.iso ?: "Unknown"

        downloadImage()
    }

    override fun OnGetPhotoStatisticsSuccese(result: StatisticsInfo) {
        detail_likes.text = result.likesTotal
        detail_see.text = result.viewsTotal
        detail_download.text = result.downloadTotal
    }

    override fun OnPhotoDetailError(e: Throwable) {
        ToastUtil.show(this,"请求失败，${e.toString()}$")
    }

    override fun OnPhotoStatisticsError(e: Throwable) {
        ToastUtil.show(this,"请求失败，${e.toString()}$")
    }

    /**
     * 下载
     */
    fun downloadImage() {
        RxView.clicks(btn_download)
                .compose<Boolean>(mRxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .filter { aBoolean -> aBoolean }
                .filter {
                    if (FileUtil.isExistsImage(mPhotoInfo.links.download)) {
                        ToastUtil.showSnackBar(this@PhotoDetailActivity, image_photo,
                                "文件已经下载了哦,到 ../JPhoto/download 查看吧")
                        return@filter false
                    }
                    return@filter true
                }
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val downloadDialog: DownloadDialog = DownloadDialog()
                    val bundle: Bundle = Bundle()
                    bundle.putString("downloadUrl", mPhotoInfo.links.download)
                    downloadDialog.arguments = bundle
                    downloadDialog.show(supportFragmentManager, "DownloadDialog")
                }, {
                    ToastUtil.show(this@PhotoDetailActivity, "下载失败,请重试")
                })
    }

    /**
     * 分享
     */
    fun shareMsg() {
        val textIntent = Intent(Intent.ACTION_SEND)
        textIntent.type = "text/plain"
        var shareLink: String
        if (!mPhotoInfo.links.html.contains("https")) {
            shareLink = mPhotoInfo.links.html.replace("http", "https")
        } else {
            shareLink = mPhotoInfo.links.html
        }
        textIntent.putExtra(Intent.EXTRA_TEXT, "分享自" + getString(R.string.app_name) + "\n拍摄自" + mPhotoInfo.user.name + "\n于 " + shareLink)
        startActivity(Intent.createChooser(textIntent, "分享"))
    }

    /**
     * 设置壁纸
     */
    fun setWallpaper() {
        showLoadingDialog()
        Glide.with(this).load(mPhotoInfo.urls.regular).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        mWallpaperManager.setBitmap(resource)
                        dismissDialog()
                        ToastUtil.showSnackBar(this@PhotoDetailActivity, btn_wallpaper, "设置壁纸成功")
                    }

                    override fun onDestroy() {
                        super.onDestroy()
                        dismissDialog()
                    }
                })
    }

}