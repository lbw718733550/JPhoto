package com.lbw.jphoto.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.lbw.jphoto.widget.CircleImageView
import java.lang.Exception

/**
 * Created by del on 17/7/27.
 * 加载图片
 */
object ImageLoadUtil {

    fun Imageload(glide: RequestManager, path:String, color1:String, color2: String, imageView:ImageView)
    {
        try {
            glide.load(path)
                    .placeholder(Color.parseColor(color1))
                    .error(Color.parseColor(color2))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
        } catch(e: Exception) {
        }
    }


    fun ImageloadWithListener(glide: RequestManager,path:String ,color1:String,color2:String,imageView:ImageView)
    {
        var color:Int = Color.parseColor(color1)
        glide.load(path)
                .placeholder(color)
                .error(Color.parseColor(color2))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        imageView.setImageResource(color)
                        return false
                    }
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        imageView.setImageDrawable(resource)
                        return false
                    }
                })
                .into(imageView)
    }

    fun ImageLoadResultBitmap(glide: RequestManager, path:String, color1:String, color2: String, imageView:ImageView,simpleTarget:SimpleTarget<Bitmap>){
        glide.load(path).asBitmap()
                .placeholder(Color.parseColor(color1))
                .error(Color.parseColor(color2))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(simpleTarget)
    }
}