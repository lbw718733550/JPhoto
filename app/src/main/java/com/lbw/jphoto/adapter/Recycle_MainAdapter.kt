package com.lbw.jphoto.adapter

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import android.widget.RelativeLayout
import com.lbw.jphoto.R
import com.lbw.jphoto.service.ImageLoadUtil
import com.lzx.nickphoto.bean.PhotoInfo

/**
 * Created by del on 17/7/27.
 */
class Recycle_MainAdapter( context: Context, layoutId: Int, data: ArrayList<PhotoInfo>?) : BaseRecycleViewAdapter_LoadMore<PhotoInfo>(context, layoutId, data) {




    override fun convert(holder: BaseViewHolder, position: Int, bean: PhotoInfo) {
        holder.setbackgrounpColor<RelativeLayout>(R.id.root_layout,Color.parseColor(bean.color))
        holder.setImageLoader(R.id.image_photo,bean.urls.small,bean.color,bean.color)
        holder.setText(R.id.image_title,bean.user.name)

    }
}