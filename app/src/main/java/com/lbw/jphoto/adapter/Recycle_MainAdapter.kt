package com.lbw.jphoto.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.lbw.jphoto.R
import com.lbw.jphoto.bean.PhotoInfo

/**
 * Created by del on 17/7/27.
 */
class Recycle_MainAdapter( context: Context, layoutId: Int,footviewlayoutId:Int, data: ArrayList<PhotoInfo>?) : BaseRecycleViewAdapter_LoadMore<PhotoInfo>(context, layoutId,footviewlayoutId, data) {


    lateinit  var onMyClickListener:OnMyClickListener


    override fun convert_FootView(holder: BaseViewHolder, position: Int) {
        when (load_more_status) {
            PULLUP_LOAD_MORE ->{ holder.setViewVisibility<RelativeLayout>(R.id.rl_footview,View.VISIBLE)}
            LOADING_MORE -> { holder.setViewVisibility<RelativeLayout>(R.id.rl_footview,View.VISIBLE)}
            NO_MORE_LOADING ->{ holder.setViewVisibility<RelativeLayout>(R.id.rl_footview,View.GONE)}
        }
    }


    override fun convert(holder: BaseViewHolder, position: Int, bean: PhotoInfo) {
        holder.setbackgrounpColor<RelativeLayout>(R.id.root_layout,Color.parseColor(bean.color))
        var photoImaage:ImageView = holder.setImageLoader(R.id.image_photo,bean.urls.small,bean.color,bean.color)

        photoImaage.setOnClickListener {
            if (onMyClickListener!=null) {
                onMyClickListener.onImageViewClick(photoImaage,position,bean)
            }
        }

        holder.setText(R.id.image_title,bean.user.name)
        var downImageBtn:ImageView =  holder.getView<ImageView>(R.id.download_btn)
        downImageBtn.setOnClickListener {
            if (onMyClickListener!=null) {
                onMyClickListener.onDownClick(downImageBtn,position,bean)
            }
        }
    }

    fun setonMyClickListener(onMyClickListener:OnMyClickListener ){
        this.onMyClickListener = onMyClickListener
    }

    interface OnMyClickListener{
        fun onImageViewClick(v:View,position: Int, bean: PhotoInfo)
        fun onDownClick(v:View,position: Int, bean: PhotoInfo)
    }
}