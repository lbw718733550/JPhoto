package com.lbw.jphoto.service

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by del on 17/7/27.
 */
object ImageLoadUtil {

    fun Imageload(context:Context,path:String ,color1:String,color2:String,imageView:ImageView)
    {
        Glide.with(context).load(path)
                .placeholder(Color.parseColor(color1))
                .error(Color.parseColor(color2))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }
}