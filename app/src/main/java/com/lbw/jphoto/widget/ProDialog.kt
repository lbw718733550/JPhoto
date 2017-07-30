package com.lbw.jphoto.widget

import com.lbw.jphoto.R
import com.lbw.jphoto.common.BaseDialog
import com.lbw.jphoto.utils.ToastUtil


/**
 * Created by lzx on 2017/7/7.
 */
class ProDialog : BaseDialog() {
    override fun getLayoutResId(): Int {
        return R.layout.layout_dialog
    }

    override fun initView() {
        isCancelable = false
    }

    override fun onResume() {
        super.onResume()
        dialog.window.setLayout(ToastUtil.dip2px(activity, 150f),ToastUtil.dip2px(activity, 150f))
    }
}