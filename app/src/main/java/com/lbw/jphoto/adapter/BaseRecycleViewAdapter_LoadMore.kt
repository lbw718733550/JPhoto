package com.lbw.jphoto.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wang.avi.AVLoadingIndicatorView



/**
 * Created by lin on 16/12/28.
 */
abstract class BaseRecycleViewAdapter_LoadMore<T>(private val context: Context, private val layoutId: Int,private val footviewlayoutId: Int, private var data: ArrayList<T>?) : RecyclerView.Adapter<BaseViewHolder>() {
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

    private var view_footView: View? = null
    private val TYPE_FOOTER = 1
    private val TYPE_ITEM = 2
    private val TAG = 0x123456
    //上拉加载更多
    val PULLUP_LOAD_MORE = 0
    //正在加载中
    val LOADING_MORE = 1
    //加载完毕没有更多数据
    val NO_MORE_LOADING = 2
    //上拉加载更多状态-默认为2
    var load_more_status = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == TYPE_ITEM){
            val v = LayoutInflater.from(context).inflate(layoutId, parent, false)
            val holder = BaseViewHolder(v, context,"type_item")

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
        }else{
            if (viewType == TYPE_FOOTER){
                val view_footView = LayoutInflater.from(context).inflate(footviewlayoutId, parent, false)
                val footViewHolder = BaseViewHolder(view_footView, context,"type_footview")
                return footViewHolder
            }
        }
        return null!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if("type_item".equals(holder.tag)){
            convert(holder, position, data!![position])
        }else{
            if ("type_footview".equals(holder.tag)){
                convert_FootView(holder, position)
            }
        }
    }




    protected abstract fun convert(holder: BaseViewHolder, position: Int, bean: T)

    protected abstract fun convert_FootView(holder: BaseViewHolder, position: Int)

    override fun getItemCount(): Int {
        return data!!.size + 1
    }


    override fun getItemViewType(position: Int): Int {
        if (position + 1 == itemCount) {
            return TYPE_FOOTER
        } else {
            return TYPE_ITEM
        }
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
    fun addAll(result: ArrayList<T>) {
        data!!.addAll(result)
        notifyDataSetChanged()
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;

     * @param status
     */
    fun changeMoreStatus(status: Int) {
        load_more_status = status
    }


}
