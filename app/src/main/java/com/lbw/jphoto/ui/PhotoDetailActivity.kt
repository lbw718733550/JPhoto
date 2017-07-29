package com.lbw.jphoto.ui

import android.app.WallpaperManager
import android.view.View
import com.lbw.jphoto.R
import com.lbw.jphoto.bean.PhotoInfo
import com.lbw.jphoto.service.ImageLoadUtil
import kotlinx.android.synthetic.main.activity_photo_detail.*
import com.lbw.jphoto.widget.ParallaxScrollView

/**
 * Created by del on 17/7/28.
 */
class PhotoDetailActivity : BaseActivity() ,View.OnClickListener{


    override fun setContentViewId(): Int {
        return R.layout.activity_photo_detail
    }

     lateinit var photoId: String
     lateinit var photoUrl: String
     lateinit var photoColor: String
     lateinit var mPhotoInfo: PhotoInfo
     lateinit var mWallpaperManager: WallpaperManager  //壁纸管理器


    override fun init() {
        photoId = intent.getStringExtra("photoId")
        photoUrl = intent.getStringExtra("photoUrl")
        photoColor = intent.getStringExtra("photoColor")

        mWallpaperManager = WallpaperManager.getInstance(this)


        //视差效果
        myscrollView.setScrollViewListener(object : ParallaxScrollView.ScrollviewListener {
            override fun onScrollChanged(scrollView: ParallaxScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                image_photo.scrollTo(x, -y / 3)
            }
        })
        //加载图片
        ImageLoadUtil.ImageloadWithListener(this,photoUrl,photoColor,photoColor,image_photo)


    }


    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }

}