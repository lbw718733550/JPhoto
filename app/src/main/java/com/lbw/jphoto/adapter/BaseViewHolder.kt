/**
 * Copyright 2013 Joan Zapata

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lbw.jphoto.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.ColorInt
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.SparseArray
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lbw.jphoto.service.ImageLoadUtil


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
class BaseViewHolder(itemView: View, internal var context: Context ,var tag:String?=null) : RecyclerView.ViewHolder(itemView) {
    var convertView: View
        internal set
    private val views: SparseArray<View>

    init {
        this.convertView = itemView
        this.views = SparseArray<View>()
    }



    fun <T : View> setViewBind(viewId: Int): T {
        val view = convertView.findViewById(viewId) as T
        return view
    }

    /**
     * textview
     * @param id
     * *
     * @param text
     */
    fun setText(id: Int, text: String): TextView {
        val tx = convertView.findViewById(id) as TextView
        tx.text = text

        return tx
    }

    /**
     * text 隐藏显示
     * @param id
     * *
     * @param state
     * *
     * @return
     */
    fun setTextVisibility(id: Int, state: Int): TextView {
        val tx = convertView.findViewById(id) as TextView
        if (state == 0) {
            tx.visibility = View.GONE
        } else {
            tx.visibility = View.VISIBLE
        }
        return tx
    }

    /**
     * imageview  src
     * @param id
     * *
     * @param resouceId
     */
    fun setImageResource(id: Int, resouceId: Int): ImageView {
        val img = convertView.findViewById(id) as ImageView
        img.setImageResource(resouceId)
        return img
    }

    /**
     * view  backgroundResource
     * @param id
     * *
     * @param resouceId
     */
    fun <T:View>setbackgrounpResource(id: Int,resouceId: Int): T {
        val view = convertView.findViewById(id) as T
        view.setBackgroundResource(resouceId)
        return view
    }
    /**
     * view  backgroundColor
     * @param id
     * *
     * @param resouceId
     */
    fun <T:View>setbackgrounpColor(id: Int,@ColorInt color:Int): T {
        val view = convertView.findViewById(id) as T
        view.setBackgroundColor(color)
        return view
    }


    /**
     * imageview  imageloader  path
     */
    fun setImageLoader(id: Int, imgPath: String,color1:String,color2:String): ImageView {
        val img = convertView.findViewById(id) as ImageView
        ImageLoadUtil.Imageload(context,imgPath,color1,color2,img)
        return img

    }
    //    /**
    //     * imageview  image
    //     */
    //    public ImageView setImageLoaderShearSize(int id, String imgPath,int width,int height)
    //    {
    //        ImageView img= (ImageView) convertView.findViewById(id);
    //        ImageLoader.getInstance().displayImage(imgPath,img, MyApplication.defaultOptions);
    ////        ImageLoadUtil.ImageLoad(context,imgPath,img);
    //        return img;
    //
    //    }
    /**
     * imageview  image  bitmap
     */
    fun setImageLoader(id: Int, bitmap: Bitmap): ImageView {
        val img = convertView.findViewById(id) as ImageView
        img.setImageBitmap(bitmap)
        return img

    }


    fun <T : View> getView(viewId: Int): T {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }

    /**
     * T 的隐藏显示
     * @param viewId
     * *
     * @param state
     * *
     * @param <T>
     * *
     * @return
    </T> */
    fun <T : View> setViewVisibility(viewId: Int, state: Int): T {
        val view = convertView.findViewById(viewId) as T
        view.visibility = state
        return view
    }


    /**
     * 按钮监听
     * @param viewId
     * *
     * @param onClickListener
     * *
     * @return
     */
    fun setBottonOnClickListener(viewId: Int, onClickListener: View.OnClickListener): Button {
        val view = convertView.findViewById(viewId) as Button
        view.setOnClickListener(onClickListener)
        return view
    }

    /**
     * LinearLayout监听
     * @param viewId
     * *
     * @param onClickListener
     * *
     * @return
     */
    fun setLinearLayoutOnClickListener(viewId: Int, onClickListener: View.OnClickListener): LinearLayout {
        val view = convertView.findViewById(viewId) as LinearLayout
        view.setOnClickListener(onClickListener)
        return view
    }


    /**
     * CheckBox 选框状态
     * @param viewId
     * *
     * @param type
     * *
     * @return
     */
    fun setCheckBox(viewId: Int, type: Boolean): CheckBox {
        val checkBox = convertView.findViewById(viewId) as CheckBox
        checkBox.isChecked = type

        return checkBox
    }

    /**
     * CheckBox 显示隐藏
     * @param viewId
     * *
     * @param state
     * *
     * @return
     */
    fun setCheckBoxVisibility(viewId: Int, state: Int): CheckBox {
        val checkBox = convertView.findViewById(viewId) as CheckBox
        checkBox.visibility = state
        return checkBox
    }

    /**
     * CheckBox 显示隐藏 动画
     * @param viewId
     * *
     * @param state
     * *
     * @return
     */
    fun setCheckBoxVisibility(viewId: Int, state: Int, animationIn: Animation, animationOut: Animation): CheckBox {
        val checkBox = convertView.findViewById(viewId) as CheckBox
        if (state == View.VISIBLE) {
            checkBox.visibility = state
            checkBox.animation = animationIn
        } else {
            checkBox.visibility = state
            checkBox.animation = animationOut
        }
        return checkBox
    }


}
