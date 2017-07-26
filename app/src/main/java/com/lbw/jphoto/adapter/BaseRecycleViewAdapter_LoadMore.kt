package com.lbw.jphoto.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by lin on 16/12/28.
 */
abstract class BaseRecycleViewAdapter_LoadMore<T>(private val context: Context, private val layoutId: Int, private var data: ArrayList<T>?) : RecyclerView.Adapter<BaseViewHolder>() {
/**

 * @param context //上下文
 * *
 * @param layoutId  //布局id
 * *
 * @param data  //数据源
 */

    private var onItemClickListner: OnItemClickListner<T>? = null//单击事件
    private var onItemLongClickListner: OnItemLongClickListner? = null//长按单击事件
    private var clickFlag = true//单击事件和长单击事件的屏蔽标识


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val v = LayoutInflater.from(context).inflate(layoutId, parent, false)
        val holder = BaseViewHolder(v, context)
        //单击事件回调
        v.setOnClickListener { v ->
            if (onItemClickListner != null) {
                onItemClickListner!!.onItemClickListner(v, holder.layoutPosition, data!![holder.layoutPosition])
            }
            clickFlag = true
        }
        //单击长按事件回调
        v.setOnLongClickListener { v ->
            if (onItemLongClickListner != null) {
                onItemLongClickListner!!.onItemLongClickListner(v, holder.layoutPosition)
            }
            clickFlag = false
            false
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        convert(holder, position, data!![position])
    }

    protected abstract fun convert(holder: BaseViewHolder, position: Int, bean: T)

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun setOnItemClickListner(onItemClickListner: OnItemClickListner<T>) {
        this.onItemClickListner = onItemClickListner
    }

    fun setOnItemClickListner(onItemClickListner: OnItemClickListner<T>, onItemLongClickListner: OnItemLongClickListner) {
        this.onItemClickListner = onItemClickListner
        this.onItemLongClickListner = onItemLongClickListner
    }

    fun setOnItemLongClickListner(onItemLongClickListner: OnItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner
    }

    interface OnItemClickListner<T> {
        fun onItemClickListner(v: View, position: Int,t:T)
    }

    interface OnItemLongClickListner {
        fun onItemLongClickListner(v: View, position: Int)
    }


    fun updaterall(data: ArrayList<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<T>? {
        return this.data
    }

    fun remove(position: Int) {
        data!!.removeAt(position)
        notifyItemRemoved(position)
        if (position != data!!.size)
            notifyItemRangeChanged(position, data!!.size - position)
    }

    fun add(t: T, position: Int) {
        data!!.add(position, t)
        notifyItemInserted(position)
    }


}
